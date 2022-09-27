package com.fkmalls.meidusha.meidusha.dto.testReport;

import com.fkmalls.meidusha.meidusha.entity.testReport.TestReportEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Mapper
public interface TestReportDto {

    @Select({"<script>",
            "SELECT * FROM `meidusha_case`.`test_report` ",
            "WHERE 1=1",
            "<when test='demandName!=null and demandName !=\"\" '>",
            "AND demand_name like concat('%',#{demandName},'%')",
            "</when>",
            "<when test='testConclusion!=null and testConclusion !=\"\" '>",
            "AND test_conclusion like concat('%',#{testConclusion},'%')",
            "</when>",
            "<when test='testUser!=null and testUser !=\"\" '>",
            "AND test_user = #{testUser}",
            "</when>",
            " ORDER BY create_time DESC;",
            "</script>"})
    List<TestReportEntity> selectAllTestReport(TestReportEntity testReportEntity);

    @Select(value = "SELECT * FROM `meidusha_case`.`test_report` WHERE id=#{id};")
    TestReportEntity selectTestReport(
            @Param("id") Long id
    );

    //INSERT INTO `meidusha_case`.`test_report` (`id`,`test_user`,`test_conclusion`,`test_plan`,`demand_name`,`test_start_time`,`test_end_time`,`test_explain`,`risk_explain`,`test_range`,`performance_result`,`perfor_report_address`,`benchmark_result`,`benchmark_report_address`,`smoke_pass_num`,`smoke_test_address`,`create_user`) VALUES (1111,'徐凤年','通过','暂无','测试需求名字','2022-08-18 19:02:40','2022-08-18 19:02:49','测试说明','风险说明','测试范围',10,'暂无性能测试报告地址',10,'暂无基准测试报告地址',100,'暂无冒烟地址','徐凤年');
    @Insert(value = "INSERT INTO `meidusha_case`.`test_report` (" +
            "`id`,`test_user`,`test_user_name`,`test_conclusion`,`test_plan`,`demand_name`,`demand_id`,`send_to_ali_emails`,`test_start_time`,`test_end_time`,`test_explain`,`risk_explain`,`test_range`,`performance_result`,`perfor_report_address`,`benchmark_result`,`benchmark_report_address`,`smoke_pass_num`,`smoke_test_address`,`create_user`) " +
            "VALUES (#{id},#{testUser},#{testUserName},#{testConclusion},#{testPlan},#{demandName},#{demandId},#{sendToAliEmails},#{testStartTime},#{testEndTime},#{testExplain},#{riskExplain},#{testRange},#{performanceResult},#{perforReportAddress},#{benchmarkResult},#{benchmarkReportAddress},#{smokePassNum},#{smokeTestAddress},#{createUser});")
    int addTestReport(TestReportEntity testReportEntity);

    @Update(value = "UPDATE `meidusha_case`.`test_report` " +
            "SET `test_user` = #{testUser},`test_user_name` = #{testUserName}, `test_conclusion` = #{testConclusion}, `test_plan` = #{testPlan}, `demand_name` = #{demandName}, `send_to_ali_emails`=#{sendToAliEmails},`test_start_time` = #{testStartTime}, `test_end_time` = #{testEndTime}, `test_explain` = #{testExplain}, `risk_explain` = #{riskExplain}, `test_range` = #{testRange}, `performance_result` =#{performanceResult}, `perfor_report_address` = #{perforReportAddress}, `benchmark_result` =#{benchmarkResult}, `benchmark_report_address` = #{benchmarkReportAddress}, `smoke_pass_num` = #{smokePassNum}, `smoke_test_address` = #{smokeTestAddress} ,`update_user` = #{updateUser} " +
            "WHERE `id` = #{id};")
    int updateTestReport(TestReportEntity testReportEntity);

    @Update(value = "UPDATE `meidusha_case`.`test_report` SET `send_email_status`=1 WHERE `id`=#{id};")
    int updateSendEmailStatus(TestReportEntity testReportEntity);
}
