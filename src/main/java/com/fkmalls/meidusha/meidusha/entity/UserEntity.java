package com.fkmalls.meidusha.meidusha.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: djm
 * @date: 2022年07月26日 18:21
 * 用户实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;

    /***
     * 昵称
     */
    private String nickName;

    /**
     * 用户名/账户
     */
    private String userName;

    private String password;
    /**
     * 关联角色ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long roleId;
    /**
     * 用户关联钉钉手机号码
     */
    private String dingMobile;
    /**
     * 用户关联阿里邮箱
     */
    private String aliEmails;
    /**
     * 头像地址
     */
    private String imagePath;
    /***
     * 删除标志 0=存在，1=不存在
     */
    private int delTag;
    /***
     * 在线状态 0=在线，1=下线
     */
    private int state;

    private String createUser;

    private LocalDateTime createTime;

    private  String userToken;
}
