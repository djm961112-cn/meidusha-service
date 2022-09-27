package com.fkmalls.meidusha.meidusha.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    private String userUrl;//用户头像
    private String userName; //用户名
    private String operation; //操作
    private String httpMethod;// HTTP Method
    private String classMethod; //方法名
    private String request; //请求
    private String response;//返回
    private int status;//请求code
    private String url; //url地址
    private LocalDateTime createDate; //操作时间
}