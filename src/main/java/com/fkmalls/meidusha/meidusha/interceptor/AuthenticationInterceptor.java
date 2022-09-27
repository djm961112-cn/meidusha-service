package com.fkmalls.meidusha.meidusha.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fkmalls.meidusha.meidusha.auth.PassToken;
import com.fkmalls.meidusha.meidusha.auth.UserLoginToken;
import com.fkmalls.meidusha.meidusha.constants.enums.StatusCode;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.exception.CaseServerException;
import com.fkmalls.meidusha.meidusha.service.TokenUtilService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * dengjinming
 * 2022/8/3
 */

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    TokenUtilService tokenUtilService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //log.info("您已经入拦截器了。。。。");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();//获取请求的映射方法(也就是hello方法)
        //检查映射方法是否有passToken注解，有则跳过认证，判断该请求映射方法上是否有passToken注解
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                //log.info("进入了@PassToken注解的方法");
                return true;
            }
        }

        String tokenY = null;
        try {
            tokenY = request.getHeader("authorization");
        } catch (Exception e) {
            log.info("找不到Authorization:"+e);
            e.printStackTrace();
            throw new CaseServerException("找不到Authorization",StatusCode.TOKEN_ERROR);
        }
        String token= null;
        try {
            token = tokenY.split(" ")[1];
        } catch (Exception e) {
            log.info("token提取失败"+e);
            log.info("获取到的token原文："+tokenY);
            e.printStackTrace();
            throw new CaseServerException("token提取失败",StatusCode.TOKEN_ERROR);
        }

        //如果没有@UserLoginToken注解，则校验token，即不存在@UserLoginToken和@PassToken，也需要校验token
        if (!method.isAnnotationPresent(UserLoginToken.class)) {
            //log.info("进入了啥也没有的方法");
            return authToken(token);
        }

        //如果不是映射到方法直接通过(不是映射方法指的是没有说明请求路径的方法，如controller中的普通方法)
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //检查映射方法是否有@UserLoginToken注解，有则进行token认证对比
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);

            if (userLoginToken.required()) {
                //执行认证
                //log.info("进入了@UserLoginToken注解的方法");
                return authToken(token);
            }
        }

        log.info("return false");
        return false;

    }

    public boolean authToken(String token){
        if (token == null) {
            log.info("缺少token，请重新登录:"+token);
            throw new CaseServerException("缺少token，请重新登录",StatusCode.TOKEN_ERROR);
        }

        Long userId;

        try {
            userId = Long.parseLong(JWT.decode(token).getAudience().get(0));
            log.info(String.valueOf("userId:"+userId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CaseServerException("token验证失败",StatusCode.TOKEN_ERROR);
        }

        UserEntity userEntity = null;
        try {
            userEntity = userService.findUserByUserId(userId).get(0);
            //log.info("用户信息："+userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(userEntity == null){
            throw new CaseServerException("用户不存在，请重新登录!",StatusCode.TOKEN_ERROR);
        }else {
            if (tokenUtilService.equalsExpiresTime(token)){
                log.info("token过期，请重新登录");
                log.info("请求用户现在的token："+userEntity.getUserToken());
                log.info("请求的token："+token);
                throw new CaseServerException("token过期，请重新登录",StatusCode.TOKEN_ERROR);
            }
        }
        //验证token    verifier:校验机    Algorithm：算法
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userEntity.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CaseServerException("token验证失败",StatusCode.TOKEN_ERROR);
        }
        return true;
    }

}
