package com.fkmalls.meidusha.meidusha.entity;

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
 * @author: djm
 * @date: 2022年06月08日 11:40
 *
 * 测试用例实体 case
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseEntity {

    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /**
    * 父ID
    * */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long pId;
    /**
    * 测试用例标题
    * */
    private String caseTitle;

    /**
    * 测试用例编码
    * */
    private String caseCode;
    /**
    * 前置条件
    * */
    private String caseCondition;
    /**
     * 预期结果
     */
    private String caseExpectedResults;
    /**
     * 实际结果
     */
    private String caseActuallyResults;
    /**
     * 操作步骤
     */
    private String caseOperationSteps;
    /**
     * 用例标签 0=正式，1=冒烟
     */
    private int caseTag=0;
    /**
         * 测试用例状态，在测试任务执行查询用，用例状态 0=未执行， 10=通过 ，20=失败 ，30=暂缓
     */
    private int caseStatus;
    /**
     * 删除标记 0=存在，1=删除
     */
    private int caseDelTag=0;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     * */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     * */
    private LocalDateTime updateTime;
    /**
     * 树状结构存子节点
     */
    private List<CaseEntity> childrens;
}
