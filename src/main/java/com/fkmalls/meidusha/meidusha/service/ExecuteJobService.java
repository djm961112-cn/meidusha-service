package com.fkmalls.meidusha.meidusha.service;

import com.fkmalls.meidusha.meidusha.dto.ExecuteDto;
import com.fkmalls.meidusha.meidusha.entity.ExecuteJobEntity;
import com.fkmalls.meidusha.meidusha.util.CaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ExecuteJobService implements ExecuteDto {
    @Autowired
    private ExecuteDto executeDto;

    @Autowired
    CaseUtil caseUtil;

    //查询所有测试执行任务
    @Override
    public List<ExecuteJobEntity> selectExecuteJobEntity(ExecuteJobEntity executeJobEntity){
        List<ExecuteJobEntity> executeJobEntityList=executeDto.selectExecuteJobEntity(executeJobEntity);
        for (ExecuteJobEntity executeJobEntityNew : executeJobEntityList){
            executeJobEntityNew.setCaseStatus(caseUtil.getCaseStatusByExecuteJobId(executeJobEntityNew.getId(),executeJobEntityNew.getCaseNumber()));
        }
        return executeJobEntityList;
    }

    //创建测试执行任务
    @Override
    public int addExecuteJob(ExecuteJobEntity executeJobEntity){
        return executeDto.addExecuteJob(executeJobEntity);
    }
}
