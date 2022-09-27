package com.fkmalls.meidusha.meidusha.dto.testReport;

import com.fkmalls.meidusha.meidusha.entity.testReport.TestProjectEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Mapper
public interface TestProjectDto {

    @Select(value = "SELECT * FROM `meidusha_case`.`test_project` WHERE test_report_id=#{testReportId};")
    List<TestProjectEntity> selectTestProjectByTestReportId(
            @Param("testReportId") Long testReportId
    );

    //INSERT INTO `meidusha_case`.`test_project` (`id`,`test_report_id`,`project_nmae`,`branch_name`,`gitlab_address`,`app_defect_message`,`coverage_test_message`,`unit_test_message`) VALUES (123456798,1111,'fkmalls-goods','master','http://','暂无','暂无','暂无');
    @Insert(value = "INSERT INTO `meidusha_case`.`test_project` (`id`,`test_report_id`,`project_nmae`,`branch_name`,`gitlab_address`,`app_defect_message`,`coverage_test_message`,`unit_test_message`) " +
            "VALUES (#{id},#{testReportId},#{projectNmae},#{branchName},#{gitlabAddress},#{appDefectMessage},#{coverageTestMessage},#{unitTestMessage});")
    int addTestProject(TestProjectEntity testProjectEntity);

    @Update(value = "UPDATE `meidusha_case`.`test_project` " +
            "SET `test_report_id` = 1015297990463586304, `project_nmae` = 'fkmalls-goods', `branch_name` = 'master', `gitlab_address` = 'http://', `app_defect_message` = '暂无', `coverage_test_message` = '暂无', `unit_test_message` = '暂无' " +
            "WHERE `id` = #{id};")
    int updateTestProject(TestProjectEntity testProjectEntity);
}
