package com.fkmalls.meidusha.meidusha.entity.demand;

/**
 * dengjinming
 * 2022/9/1
 */
public interface DemandStatusInterface {
    /**
     * 状态码
     */
    String getCode();

    /**
     * 状态描述
     */
    String getMessage();

    /**
     * 状态描述
     */
    String getMessage(Object... format);
}
