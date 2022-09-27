package com.fkmalls.meidusha.meidusha.auth;

import java.lang.annotation.*;

/**
 * dengjinming
 * 2022/8/3
 */
@Target({ElementType.METHOD,ElementType.TYPE})//可以给一个方法进行注解，可以给一个类进行注解
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented
public @interface UserLoginToken {
    //该参数是否必须，如果true的话，当请求中没有传递该参数就报错。
    // 就是请求的url路径上如果需要参数的话是否必须传参(跟@RequestParam中的required是否需要参数一样)，例：如果是/hello/{id}则必须传个id值
    boolean required() default true;
}
