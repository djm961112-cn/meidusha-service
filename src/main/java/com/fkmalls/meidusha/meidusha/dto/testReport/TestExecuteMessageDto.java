package com.fkmalls.meidusha.meidusha.dto.testReport;

import com.fkmalls.meidusha.meidusha.entity.testReport.TestExecuteMessageEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Mapper
public interface TestExecuteMessageDto {

    //新建测试执行信息
    //INSERT INTO `meidusha_case`.`test_execute_message` (`id`,`test_type`,`start_time`,`end_time`,`plan_execute_case_all_num`,`rel_execute_case_all_num`,`rel_pass_case_all_num`,`find_bug_num`,`fix_bug_num`,`remark`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES (1,10,'2022-08-18 19:07:45','2022-08-18 19:07:47',30,30,30,3,3,'暂无','徐凤年','2022-08-18 19:08:20','无','2022-08-18 19:08:25');
    @Insert(value = "INSERT INTO `meidusha_case`.`test_execute_message` (`id`,`test_report_id`,`test_type`,`start_time`,`end_time`,`plan_execute_case_all_num`,`rel_execute_case_all_num`,`rel_pass_case_all_num`,`find_bug_num`,`fix_bug_num`,`remark`,`create_user`) " +
            "VALUES (#{id},#{testReportId},#{testType},#{startTime},#{endTime},#{planExecuteCaseAllNum},#{relExecuteCaseAllNum},#{relPassCaseAllNum},#{findBugNum},#{fixBugNum},#{remark},#{createUser});")
    int addTestExecuteMessage(TestExecuteMessageEntity testExecuteMessageEntity);

    //SELECT * FROM `meidusha_case`.`test_execute_message` WHERE test_report_id='1111';
    @Select(value = "SELECT * FROM `meidusha_case`.`test_execute_message` WHERE test_report_id=#{testReportId};")
    List<TestExecuteMessageEntity> selectTestExecuteMsgByTestReportId(
            @Param("testReportId")Long testReportId
    );

    @Update(value = "UPDATE `meidusha_case`.`test_execute_message` " +
            "SET  `test_type` = #{testType}, `start_time` = #{startTime}, `end_time` = #{endTime}, `plan_execute_case_all_num` = #{planExecuteCaseAllNum}, `rel_execute_case_all_num` = #{relExecuteCaseAllNum}, `rel_pass_case_all_num` = #{relPassCaseAllNum}, `find_bug_num` = #{findBugNum}, `fix_bug_num` = #{fixBugNum}, `remark` = #{remark}, `update_user` = #{updateUser} " +
            "WHERE `id`= #{id};")
    int updateTestExecuteMsgByTestReportId(TestExecuteMessageEntity testExecuteMessageEntity);

}
