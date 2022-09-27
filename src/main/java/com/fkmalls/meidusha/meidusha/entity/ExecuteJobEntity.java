package com.fkmalls.meidusha.meidusha.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 *  测试用例执行实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteJobEntity {
    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /***
     *  用例执行任务名称
     */
    private String executeName;
    /**
     * 任务关联用例id
     */
    private List<Long> caseIdList;
    /***
     *  用例执行任务人
     */
    private String executeUser;

    private String executeUserName;
    /**
     * 执行任务类型 0=普通，1=冒烟
     */
    private int executeType;
    /***
     *  用例执行任务开始时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime executeStartTime;
    /**
     * 对应测试任务执行状态
     */
    private Map caseStatus;
    /**
     * 测试任务关联测试用例总数
     */
    private int caseNumber;
    /***
     *  用例执行任务创建人
     */
    private String createUser;
    /***
     *  用例执行任务创建时间
     */
    private LocalDateTime createTime;
    /***
     *  用例执行任务更新人
     */
    private String updateUser;
    /***
     *  用例执行任务更新时间
     */
    private LocalDateTime updateTime;
    /***
     *  用例执行任务删除标志 0=存在，1=删除
     */
    private int delTag;
}
