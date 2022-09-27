package com.fkmalls.meidusha.meidusha.dto;

import com.fkmalls.meidusha.meidusha.entity.ExecuteJobItemEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 */
@Mapper
public interface ExecuteJobItemDto {

    //INSERT INTO `meidusha_case`.`execute_job_item` (`id`,`case_id`,`execute_id`,del_tag) VALUES (1001862582656565299,1001862582681731072,1001862582656565249,0);
    @Insert(value = "INSERT INTO `meidusha_case`.`execute_job_item` (`id`,`case_id`,`execute_id`,del_tag) VALUES (#{id},#{caseId},#{executeId},0);")
    int addExecuteItem(
            @Param("id") Long id,
            @Param("caseId") Long caseId,
            @Param("executeId") Long executeId
    );

    //SELECT * FROM execute_job_item WHERE execute_id='1001862582656565249';
    @Select(value = "SELECT * FROM execute_job_item WHERE execute_id=#{executeId};")
    List<ExecuteJobItemEntity> selectExecuteJobItemByExecuteId(
            @Param("executeId") Long executeId
    );

    //UPDATE `meidusha_case`.execute_job_item SET `case_status`=10 WHERE `case_id`=1;
    @Update(value = "UPDATE `meidusha_case`.execute_job_item SET `case_status`=#{caseStatus} WHERE `case_id`=#{caseId} and `execute_id`=#{executeId};")
    int executeCase(
            @Param("caseStatus") int caseStatus,
            @Param("caseId") Long caseId,
            @Param("executeId") Long executeId
    );

    @Update(value = "UPDATE `meidusha_case`.execute_job_item SET `case_status`=#{caseStatus} WHERE `case_id`=#{caseId} and `execute_id`=#{executeId};")
    int testUpdate(ExecuteJobItemEntity executeJobItemEntity);
}
