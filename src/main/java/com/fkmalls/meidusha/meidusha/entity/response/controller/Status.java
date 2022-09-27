package com.fkmalls.meidusha.meidusha.entity.response.controller;

/**
 * controller层统一响应体抽象类
 *
 * @author djm
 * @date 2022年06月23日 19:01
 */
public interface Status {
    /**
     * 是否成功状态
     *
     * @return 成功状态，返回true；否则，返回false
     */
    boolean isSuccess();

    /**
     * 状态码值
     *
     * @return 状态码值
     */
    int getStatus();

    /**
     * 错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 状态描述
     *
     * @return 状态描述
     */
    String getMessage();
    String setMessage(String message);
    /**
     * 状态描述
     *
     * @return 状态描述
     */
    String getMessage(Object... format);
}
