package com.fkmalls.meidusha.meidusha.service;

import com.fkmalls.meidusha.meidusha.dto.ExecuteJobItemDto;
import com.fkmalls.meidusha.meidusha.entity.ExecuteJobItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ExecuteJobItemService implements ExecuteJobItemDto {

    @Autowired
    ExecuteJobItemDto executeJobItemDto;

    @Override
    public int addExecuteItem(Long id,Long caseId,Long executeJobId){
        return executeJobItemDto.addExecuteItem(id,caseId,executeJobId);
    }

    @Override
    public List<ExecuteJobItemEntity> selectExecuteJobItemByExecuteId(Long executeId){
        return executeJobItemDto.selectExecuteJobItemByExecuteId(executeId);
    }

    @Override
    public int executeCase(int caseStatus,Long caseId,Long executeId){
        return executeJobItemDto.executeCase(caseStatus,caseId,executeId);
    }

    @Override
    public int testUpdate(ExecuteJobItemEntity executeJobItemEntity){
        return executeJobItemDto.testUpdate(executeJobItemEntity);
    }
}
