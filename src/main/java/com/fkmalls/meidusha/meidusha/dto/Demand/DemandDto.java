package com.fkmalls.meidusha.meidusha.dto.Demand;

import com.fkmalls.meidusha.meidusha.entity.demand.DemandEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * dengjinming
 * 2022/8/19
 */
@Mapper
public interface DemandDto {

    //INSERT INTO `meidusha_case`.`demand` (`id`,`demand_name`,`demand_address`,`product_name`,`create_user`) VALUES (9999,'需求名称','需求地址','剑钊','徐凤年');
    @Insert(value = "INSERT INTO `meidusha_case`.`demand` (`id`,`demand_name`,`demand_address`,`product_id`,`qa_id`,`dev_id`,`front_id`,`create_user`) " +
            "VALUES (#{id},#{demandName},#{demandAddress},#{productId},#{qaId},#{devId},#{frontId},#{createUser});")
    int addDemand(
            /**
             * 新增需求
             */
            @Param("id") Long id,
            @Param("demandName") String demandName,
            @Param("demandAddress") String demandAddress,
            @Param("productId") Long productId,
            @Param("qaId")Long qaId,
            @Param("devId")Long devId,
            @Param("frontId")Long frontId,
            @Param("createUser") String createUser
    );

    @Select({"<script>",
            "SELECT * FROM `meidusha_case`.`demand`",
            "WHERE demand_del_tag=0",
            "<when test='demandName!=null and demandName !=\"\" '>",
            "AND demand_name like concat('%',#{demandName},'%')",
            "</when>",
            "<when test='productId!=null and productId !=\"\"'>",
            "AND product_id = #{productId}",
            "</when>",
            "<when test='qaId!=null and qaId !=\"\"'>",
            "AND qa_id = #{qaId}",
            "</when>",
            "<when test='devId!=null and devId !=\"\"'>",
            "AND dev_id = #{devId}",
            "</when>",
            "<when test='frontId!=null and frontId !=\"\"'>",
            "AND front_id = #{frontId}",
            "</when>",
            "<when test='demandStatus!=null and demandStatus !=\"\"'>",
            "AND demand_status = #{demandStatus}",
            "</when>",
            " ORDER BY create_time DESC;",
            "</script>"})
    List<DemandEntity> selectAllDemand(DemandEntity demandEntity);

    //UPDATE `meidusha_case`.`demand` SET `demand_status`=10,`update_user`='徐凤年',`update_time`='2022-08-19 11:25:55' WHERE `id`=9999;
    @Update(value = "UPDATE `meidusha_case`.`demand` SET `demand_status`=#{demandStatus},`update_user`=#{updateUser} WHERE `id`=#{id};")
    int updateDemandStatusById(
            /**
             * 更新需求状态
             */
            @Param("demandStatus") int demandStatus,
            @Param("updateUser")String updateUser,
            @Param("id") Long id
    );

    @Update(value = "UPDATE `meidusha_case`.`demand` SET `report_status`=#{reportStatus} WHERE `id`=#{id};")
    int updateReportStatusById(
            /**
             * 更新需求是否生成测试报告状态
             */
            @Param("reportStatus") int reportStatus,
            @Param("id") Long id
    );
}
