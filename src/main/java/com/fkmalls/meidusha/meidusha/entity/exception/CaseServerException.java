package com.fkmalls.meidusha.meidusha.entity.exception;


import com.fkmalls.meidusha.meidusha.entity.response.controller.Status;

/**
 * 自定义异常类，用于封装程序内部可判断出来的Exception
 *
 * @author djm
 * @date 2022/7/30
 */
public class CaseServerException extends RuntimeException {

    private Status status;

    public CaseServerException(Status status) {
        this.status = status;
    }

    public CaseServerException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public CaseServerException(String message, Throwable cause, Status status) {
        super(message, cause);
        this.status = status;
    }

    public CaseServerException(Throwable cause, Status status) {
        super(cause);
        this.status = status;
    }

    public CaseServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Status status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
