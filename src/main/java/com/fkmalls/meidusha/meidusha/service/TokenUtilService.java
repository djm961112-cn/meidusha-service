package com.fkmalls.meidusha.meidusha.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * dengjinming
 * 2022/8/1
 */
@Service
public class TokenUtilService {
    /**
     * 根据用户名和密码，使用加密算法生成JWT的token令牌。
     */

    @Autowired
    UserService userService;

    public String getToken(UserEntity userEntity) {
        /**
         * 获取新的token
         */
        String token = "";
        token ="Bearer "+ JWT.create().withAudience(String.valueOf(userEntity.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis()+12*60*60*1000))//12小时过期
                .sign(Algorithm.HMAC256(userEntity.getPassword()));
        return token;
    }

    public Long getTokenUserId(String token){
        /**
         * 解析token中的userId
         */
        Long userId=Long.parseLong(JWT.decode(token).getAudience().get(0));
        UserEntity userEntity=userService.findUserByUserId(userId).get(0);
        return userEntity.getId();
    }

    public boolean equalsExpiresTime(String token){
        /**
         * 判断token是否过期
         */
        Date expiresTime=JWT.decode(token).getExpiresAt();
        if (expiresTime.compareTo(new Date())<0){
            return true;//expiresTime小于当前时间
        }else {
            return false;//expiresTime大于当前时间
        }
    }

    

}
