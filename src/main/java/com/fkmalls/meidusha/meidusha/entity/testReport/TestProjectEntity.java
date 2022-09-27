package com.fkmalls.meidusha.meidusha.entity.testReport;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dengjinming
 * 2022/8/18
 * 测试工程实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestProjectEntity {
    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /**
     * 测试报告id
     */
    private Long testReportId;
    /**
     * 工程名
     */
    private String projectNmae;
    /**
     * 分支名
     */
    private String branchName;
    /**
     * gitlab地址
     */
    private String gitlabAddress;
    /**
     * 应用缺陷值
     */
    private String appDefectMessage;
    /**
     * 覆盖率测试
     */
    private String coverageTestMessage;
    /**
     * 单元测试
     */
    private String unitTestMessage;

}
