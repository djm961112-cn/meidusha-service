package com.fkmalls.meidusha.meidusha.dto;

import com.fkmalls.meidusha.meidusha.entity.ExecuteJobEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
@Mapper
public interface ExecuteDto {

    //INSERT INTO `meidusha_case`.`execute_job` (`id`,`execute_name`,`execute_user`,`execute_start_time`,`create_user`) VALUES (1001862582656565250,'测试执行用例任务insert','陈祥铭','2022-07-29 16:08:50','djm');
    @Insert(value = "INSERT INTO `meidusha_case`.`execute_job` " +
            "(`id`,`execute_name`,`execute_user`,`execute_user_name`,`execute_start_time`,`create_user`,`execute_type`,`case_number`) " +
            "VALUES (#{id},#{executeName},#{executeUser},#{executeUserName},#{executeStartTime},#{createUser},#{executeType},#{caseNumber});")
    int addExecuteJob(ExecuteJobEntity executeJobEntity);

    @Select({"<script>",
            "SELECT * FROM `meidusha_case`.`execute_job` ej",
            "WHERE 1=1",
            "<when test='executeName!=null and executeName !=\"\" '>",
            "AND ej.execute_name like concat('%',#{executeName},'%')",
            "</when>",
            "<when test='executeUser!=null and executeUser !=\"\"'>",
            "AND ej.execute_user = #{executeUser}",
            "</when>",
            " ORDER BY create_time DESC;",
            "</script>"})
    List<ExecuteJobEntity> selectExecuteJobEntity(ExecuteJobEntity executeJobEntity);



}
