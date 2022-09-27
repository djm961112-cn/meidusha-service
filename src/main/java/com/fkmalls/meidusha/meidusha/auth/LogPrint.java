package com.fkmalls.meidusha.meidusha.auth;

import java.lang.annotation.*;

/**
 * dengjinming
 * 2022/8/18
 * 打印请求及返回日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //注解添加的位置
@Documented
public @interface LogPrint {
    String description() default "";
}
