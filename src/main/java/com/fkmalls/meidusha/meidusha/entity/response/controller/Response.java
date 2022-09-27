package com.fkmalls.meidusha.meidusha.entity.response.controller;

import com.fkmalls.meidusha.meidusha.constants.enums.StatusCode;
import lombok.Data;

import java.util.List;

/**
 * controller层统一响应体
 *
 * @author djm
 */
@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;
    private Integer size;

    public Response() {
        this.code = StatusCode.SERVICE_RUN_SUCCESS.getStatus();
    }

    public static <T> Response<T> build(int status, String message) {
        return build(status, message, null);
    }

    public static <T> Response<T> build(Status status, String message) {
        return build(status.getStatus(), message, null);
    }

    public static <T> Response<T> build(Status status) {
        return build(status.getStatus(), status.getMessage(), null);
    }

    public static <T> Response<T> build(int status, String message, T data) {
        Response<T> response = new Response<>();
        response.setCode(status);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> build(int status, String message, T data,Integer size) {
        Response<T> response = new Response<>();
        response.setCode(status);
        response.setMessage(message);
        response.setData(data);
        response.setSize(size);
        return response;
    }

    public static <T> Response<T> success() {
        return build(StatusCode.SERVICE_RUN_SUCCESS);
    }

    public static <T> Response<T> fixError() {
        return build(StatusCode.FIX_ERROR);
    }

    public static <T> Response<T> addError() {
        return build(StatusCode.ADD_ERROR);
    }

    public static <T> Response<T> addErrorMsg(String message) {
        return build(StatusCode.ADD_ERROR,StatusCode.ADD_ERROR.setMessage(message));
    }

    public static <T> Response<T> success(T data) {
        return build(StatusCode.SERVICE_RUN_SUCCESS.getStatus(), StatusCode.SERVICE_RUN_SUCCESS.getMessage(), data);
    }

    public static <T> Response<T> successList(List<T> list) {
        return (Response<T>) build(StatusCode.SERVICE_RUN_SUCCESS.getStatus(), StatusCode.SERVICE_RUN_SUCCESS.getMessage(), list, list.size());
    }

    public static <T> Response<T> serviceBusy(T data) {
        return build(StatusCode.SERVER_BUSY_ERROR.getStatus(), StatusCode.SERVER_BUSY_ERROR.getMessage(), data);
    }

    public static <T> Response<T> fileServiceError() {
        return build(StatusCode.FILE_GETCONTENT_REEOR.getStatus(), StatusCode.FILE_GETCONTENT_REEOR.getMessage());
    }

    public static <T> Response<T> noAuth() {
        return build(StatusCode.NO_AUTHORITY_ERROR.getStatus(), StatusCode.NO_AUTHORITY_ERROR.getMessage());
    }

}
