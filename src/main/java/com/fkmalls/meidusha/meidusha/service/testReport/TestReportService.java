package com.fkmalls.meidusha.meidusha.service.testReport;

import com.fkmalls.meidusha.meidusha.dto.testReport.TestReportDto;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Service
public class TestReportService implements TestReportDto {

    @Autowired
    TestReportDto testReportDto;
    @Autowired
    TestExecuteMessageService testExecuteMessageService;
    @Autowired
    TestProjectService testProjectService;

    @Override
    public List<TestReportEntity> selectAllTestReport(TestReportEntity testReportEntityNew){
        List<TestReportEntity> testReportEntityList=testReportDto.selectAllTestReport(testReportEntityNew);
        for (TestReportEntity testReportEntity:testReportEntityList) {
            if ( testReportEntity.getSendToAliEmails() != null && testReportEntity.getSendToAliEmails().indexOf(",") != -1 ) {
                List<String> emailsList = Arrays.asList(testReportEntity.getSendToAliEmails().split(","));
                testReportEntity.setEmailsList(emailsList);
            }else {
                List<String> emailsList=new ArrayList<>();
                emailsList.add(testReportEntity.getSendToAliEmails());
                testReportEntity.setEmailsList(emailsList);
            }
            testReportEntity.setTestExecuteMessageEntityList(testExecuteMessageService.selectTestExecuteMsgByTestReportId(testReportEntity.getId()));
            testReportEntity.setTestProjectEntityList(testProjectService.selectTestProjectByTestReportId(testReportEntity.getId()));
        }
        return testReportEntityList;
    }

    @Override
    public TestReportEntity selectTestReport(Long id){
        TestReportEntity testReport=testReportDto.selectTestReport(id);
        if ( testReport.getSendToAliEmails() != null && testReport.getSendToAliEmails().indexOf(",") != -1 ) {
            List<String> emailsList = Arrays.asList(testReport.getSendToAliEmails().split(","));
            testReport.setEmailsList(emailsList);
        }else {
            List<String> emailsList=new ArrayList<>();
            emailsList.add(testReport.getSendToAliEmails());
            testReport.setEmailsList(emailsList);
        }
        testReport.setTestExecuteMessageEntityList(testExecuteMessageService.selectTestExecuteMsgByTestReportId(id));
        testReport.setTestProjectEntityList(testProjectService.selectTestProjectByTestReportId(id));
        return testReport;
    }

    @Override
    public int addTestReport(TestReportEntity testReportEntity){
        return testReportDto.addTestReport(testReportEntity);
    }

    @Override
    public int updateTestReport(TestReportEntity testReportEntity){
        return testReportDto.updateTestReport(testReportEntity);
    }

    @Override
    public int updateSendEmailStatus(TestReportEntity testReportEntity){
        return testReportDto.updateSendEmailStatus(testReportEntity);
    }
}
