package com.fkmalls.meidusha.meidusha.service.testReport;

import com.fkmalls.meidusha.meidusha.dto.testReport.TestProjectDto;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * dengjinming
 * 2022/8/18
 */
@Service
public class TestProjectService implements TestProjectDto {

    @Autowired
    TestProjectDto testProjectDto;

    @Override
    public List<TestProjectEntity> selectTestProjectByTestReportId(Long testReportId){
        return testProjectDto.selectTestProjectByTestReportId(testReportId);
    }

    @Override
    public int addTestProject(TestProjectEntity testProjectEntity){
        return testProjectDto.addTestProject(testProjectEntity);
    }

    @Override
    public int updateTestProject(TestProjectEntity testProjectEntity){
        return testProjectDto.updateTestProject(testProjectEntity);
    }
}
