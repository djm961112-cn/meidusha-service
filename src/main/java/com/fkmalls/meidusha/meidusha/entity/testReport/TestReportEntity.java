package com.fkmalls.meidusha.meidusha.entity.testReport;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 * 测试报告实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestReportEntity {
    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    //测试基本信息
    /**
     * 测试执行人ID
     */
    private Long testUser;
    /**
     * 测试执行人昵称
     */
    private String testUserName;
    /**
     *  测试结论
     */
    private String testConclusion;
    /**
     *  关联提测单
     */
    private String testPlan;
    /**
     * 关联需求名称
     */
    private String demandName;
    /**
     * 关联需求ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    /**
     * 发送邮件状态，0=未发送过，1=发送过
     */
    private int sendEmailStatus;
    private Long demandId;
    /**
     * 数据库接受测试报告的邮箱
     */
    private String sendToAliEmails=null;
    /**
     * 前端接收及传参测试报告的邮箱列表
     */
    private List<String> emailsList;
    /**
     * 测试开始时间
     */
    private LocalDateTime testStartTime;
    /**
     * 测试结束时间
     */
    private LocalDateTime testEndTime;
    /**
     * 测试说明
     */
    private String testExplain;
    /**
     * 风险说明
     */
    private String riskExplain;
    /**
     * 测试范围
     */
    private String testRange;
    //测试执行信息
    /**
     * 测试执行信息列表
     */
    private List<TestExecuteMessageEntity> testExecuteMessageEntityList;
    //性能测试信息
    /**
     * 性能测试结果 10=无需压测，20=压测通过，30=压测不通过
     */
    private int performanceResult;
    /**
     * 性能测试报告地址
     */
    private String perforReportAddress;
    /**
     * 基准测试结果 10=无需压测，20=压测通过，30=压测不通过
     */
    private int benchmarkResult;
    /**
     * 基准测试报告地址
     */
    private String benchmarkReportAddress;
    //测试工程
    /**
     * 测试工程列表
     */
    private List<TestProjectEntity> testProjectEntityList;
    //冒烟测试情况
    /**
     * 冒烟通过率
     */
    private int smokePassNum;
    /**
     * 冒烟测试用例地址
     */
    private String smokeTestAddress;

    private String createUser;
    private LocalDateTime createTime;
    private String updateUser;
    private LocalDateTime updateTime;
}
