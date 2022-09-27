package com.fkmalls.meidusha.meidusha.constants.enums;

import com.fkmalls.meidusha.meidusha.entity.demand.DemandStatusInterface;

/**
 * dengjinming
 * 2022/9/1
 */
public enum DemandStatus implements DemandStatusInterface {
    //需求状态枚举
    DEVELOPMENT(10, "研发中"),
    TESTED(20, "已提测"),
    NOTTEST(30, "提测驳回"),
    TEST_SUCCESS(40, "测试完成"),
    RELEASE(50, "已发布")
    ;

    private int code;
    private String message;

    DemandStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return String.format(message, "");
    }

    @Override
    public String getMessage(Object... objects) {
        if (objects == null) {
            return getMessage();
        }

        return String.format(message, objects);
    }
}
