package com.fkmalls.meidusha.meidusha.entity.demand;

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
 * 2022/8/19
 * 需求实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandEntity {

    //雪花算法得到的值在前端js会溢出这里改成字符型,但数据库里存的是 bigint
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long id;
    /**
     * 需求名称
     */
    private String demandName;
    /**
     * 需求地址
     */
    private String demandAddress;
    /**
     * 关联产品
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long productId=null;
    private String productName;
    /**
     * 关联测试
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long qaId=null;
    private String qaName;
    /**
     * 关联后端
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long devId=null;
    private String devName;
    /**
     * 关联前端
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JSONField(serializeUsing = com.alibaba.fastjson.serializer.ToStringSerializer.class)
    private Long frontId=null;
    private String frontName;
    /**
     * 需求进度 10=研发中，20=已提测，30=提测驳回，40=测试完成，50=已发布
     */
    private int demandStatus;
    /**
     * 是否生成测试报告，0=未生成，1=已生成
     */
    private int reportStatus=0;
    /**
     * 删除标记 0=存在，1=删除
     */
    private int demandDelTag=0;
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
}
