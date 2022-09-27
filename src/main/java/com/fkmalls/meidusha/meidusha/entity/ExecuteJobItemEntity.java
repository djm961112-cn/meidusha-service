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
 *  测试用例执行明细实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteJobItemEntity {
    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /***
     * 关联测试用例id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long caseId;
    /***
     * 关联执行任务id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long executeId;
    /**
     * 用例状态 0=未执行， 10=通过 ，20=失败 ，30=暂缓
     */
    private int caseStatus;
    /***
     * 删除标志 0=存在，1=删除
     */
    private int delTag;
    /***
     * 创建时间
     */
    private LocalDateTime createTime;
}
