package com.fkmalls.meidusha.meidusha.util;

import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.service.TokenUtilService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * dengjinming
 * 2022/8/4
 */
@Component
@Slf4j
public class UserUtil {

    @Autowired
    UserService userService;
    @Autowired
    TokenUtilService tokenUtilService;

    public UserEntity findUserByTokenUserId(String token){
        /**
         * 通过token获取用户
         */
        Long userId=tokenUtilService.getTokenUserId(token);
        UserEntity userEntity=userService.findUserByUserId(userId).get(0);
        return userEntity;
    }


    public String randomTitle(){
        /**
         * 随机获取数据库励志短句
         */
        int num = (int)(Math.random()*500+1);//[1,500]
        if (userService.findTitle(num)==null){
            return randomTitle();
        }else {
            return userService.findTitle(num);
        }
    }

}
