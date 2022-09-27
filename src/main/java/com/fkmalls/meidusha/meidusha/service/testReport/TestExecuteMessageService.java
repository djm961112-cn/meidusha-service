package com.fkmalls.meidusha.meidusha.service.testReport;

import com.fkmalls.meidusha.meidusha.dto.testReport.TestExecuteMessageDto;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestExecuteMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Service
public class TestExecuteMessageService implements TestExecuteMessageDto {

    @Autowired
    TestExecuteMessageDto testExecuteMessageDto;

    @Override
    public  int addTestExecuteMessage(TestExecuteMessageEntity testExecuteMessageEntity){
        return testExecuteMessageDto.addTestExecuteMessage(testExecuteMessageEntity);
    }

    @Override
    public List<TestExecuteMessageEntity> selectTestExecuteMsgByTestReportId(Long testReportId){
        return testExecuteMessageDto.selectTestExecuteMsgByTestReportId(testReportId);
    }

    public int updateTestExecuteMsgByTestReportId(TestExecuteMessageEntity testExecuteMessageEntity){
        return testExecuteMessageDto.updateTestExecuteMsgByTestReportId(testExecuteMessageEntity);
    }
}
