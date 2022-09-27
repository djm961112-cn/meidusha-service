package com.fkmalls.meidusha.meidusha.entity.testReport;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * dengjinming
 * 2022/8/18
 * 测试执行信息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestExecuteMessageEntity {
    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /**
     * 测试报告id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long testReportId;
    /**
     * 测试类型 10=功能测试，20=接口测试，30=性能测试
     */
    private int testType;
    /**
     * 开始时间
     */
    private LocalDateTime startTime=null;
    /**
     * 结束时间
     */
    private LocalDateTime endTime=null;
    /**
     * 计划执行用例总数
     */
    private int planExecuteCaseAllNum=0;
    /**
     * 实际执行用例总数
     */
    private int relExecuteCaseAllNum=0;
    /**
     * 实际通过用例总数
     */
    private int relPassCaseAllNum=0;
    /**
     * 发现BUG数
     */
    private int findBugNum=0;
    /**
     * 修复BUG数
     */
    private int fixBugNum=0;
    /**
     * 备注
     */
    private String remark=null;
    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
}
