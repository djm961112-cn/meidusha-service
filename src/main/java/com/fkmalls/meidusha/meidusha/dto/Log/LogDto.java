package com.fkmalls.meidusha.meidusha.dto.Log;

import com.fkmalls.meidusha.meidusha.entity.SysLog;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * dengjinming
 * 2022/9/14
 */
@Mapper
public interface LogDto {

    @Insert(value = "INSERT INTO `meidusha_case`.`log` " +
            "(`id`,`user_url`,`user_name`,`operation`,`http_method`,`class_method`,`request`,`status`,`response`,`url`,`createDate`) " +
            "VALUES (#{id},#{userUrl},#{userName},#{operation},#{httpMethod},#{classMethod},#{request},#{status},#{response},#{url},#{createDate});")
    int saveLog(SysLog sysLog);

    @Select(value = "SELECT user_name,createDate,operation,user_url FROM `meidusha_case`.`log` WHERE status=200 ORDER BY createDate DESC LIMIT 66;")
    List<SysLog> findAllLog();

    @Update(value = "UPDATE `meidusha_case`.`log` SET `createDate`=#{createDate} WHERE `id`=#{id};")
    int updateLog(
            @Param("createDate") LocalDateTime createDate,
            @Param("id") Long id
    );

    @Select(value = "SELECT * FROM log WHERE operation='执行测试用例' AND user_name = #{userName} ORDER BY createDate DESC LIMIT 1;")
    SysLog findNewCreateDate(
            @Param("userName") String userName
    );
}
