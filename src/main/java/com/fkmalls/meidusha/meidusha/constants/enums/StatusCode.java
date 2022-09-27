package com.fkmalls.meidusha.meidusha.constants.enums;


import com.fkmalls.meidusha.meidusha.entity.response.controller.Status;

/**
 * 自定义前后端传输编码
 *
 * @author djm
 * @date 2022/7/30
 */
public enum StatusCode implements Status {
    // 枚举
    // 这里的200不建议改，如果需要改，前端组件判断success的判断也需要更改，单纯兼容，如果有代码洁癖可以改为10000
    SERVICE_RUN_SUCCESS(200, "操作成功"),
    JSON_FORMAT_ERROR(10004, "JSON格式不正确"),
    DATA_FORMAT_ERROR(10005, "数据格式化异常"),
    HTTP_ACCESS_ERROR(10006, "HTTP访问异常"),
    FILE_FORMAT_ERROR(10007, "文件格式不对，请上传xmind文件"),
    FILE_IMPORT_ERROR(10008, "导入失败，请稍后再试"),
    FILE_EXPORT_ERROR(10009, "导出失败，请稍后再试"),
    EXCEL_FORMAT_ERROR(10010, "EXCEL格式不匹配"),
    NODE_ALREADY_EXISTS(20001, "节点已存在"),
    WS_UNKNOWN_ERROR(100010, "websocket访问异常"),
    AUTHORITY_ERROR(100011, "权限认证错误"),
    NO_AUTHORITY_ERROR(10016,"请求缺少Authorization或内容为空"),
    TOKEN_ERROR(10017,"tooken异常，请重新登录"),
    ASPECT_ERROR(100012, "权限内部处理错误"),
    LOGIN_ERROR(60204,"用户名或密码错误"),
    LOGOUT_ERROR(500,"退出成功"),
    FIX_ERROR(100013,"修改失败"),
    ADD_ERROR(500,"新增失败！"),

    //case
    ADD_CASE_ERROR(100014,"缺少PID或节点内容"),
    ADD_EXECUTE_ERROR(100015,"创建测试用例执行任务失败,请稍后重试"),
    UPDATE_EXECUTE_CASE_STATUS_ERROR(100018,"更新测试用例状态失败，请重试"),

    // 内部异常
    INTERNAL_ERROR(10400, "内部参数校验或逻辑出错"),
    VERSION_NOT_MATCH(10500,"暂不支持xmind zen版本，请上传xmind 8版本文件"),
    NOT_FOUND_ENTITY(10600, "没有该项数据"),

    //文件异常
    FILE_GETCONTENT_REEOR(500,"获取content.json文件失败，请另存为新的Xmind文件后后重试。"),

    // 统一异常
    SERVER_BUSY_ERROR(500, "服务器正忙，请稍后再试");



    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return getStatus() == 10000;
    }

    @Override
    public int getStatus() {
        return code;
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
    public String setMessage(String message) {
        return this.message=message;
    }

    @Override
    public String getMessage(Object... objects) {
        if (objects == null) {
            return getMessage();
        }

        return String.format(message, objects);
    }
}
