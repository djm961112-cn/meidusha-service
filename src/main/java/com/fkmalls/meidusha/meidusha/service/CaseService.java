package com.fkmalls.meidusha.meidusha.service;

import com.fkmalls.meidusha.meidusha.dto.CaseDto;
import com.fkmalls.meidusha.meidusha.entity.CaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: djm
 * @date: 2022年06月08日 13:01
 */
@Service
public class CaseService implements CaseDto {
    @Autowired
    private CaseDto caseDto;

    @Override
    public List<CaseEntity> getCaseList() {
        return caseDto.getCaseList();
    }

    @Override
    public int saveCaseWithoutCaseCondition(Long id,Long pId, String caseCode, String caseTitle, int caseTag, int caseDelTag){
        return caseDto.saveCaseWithoutCaseCondition(id,pId,caseCode,caseTitle,caseTag,caseDelTag);
    }

    @Override
    public String findLastCode(){
        return caseDto.findLastCode();
    }

    @Override
    public Integer findLastId(){
        return caseDto.findLastId();
    }

    @Override
    public int fixCase(String caseCode,String caseTitle,String caseCondition,String caseExpectedResults,String caseActuallyResults,String caseOperationSteps,String updateUser,Long id){
        return caseDto.fixCase(caseCode,caseTitle,caseCondition,caseExpectedResults,caseActuallyResults,caseOperationSteps,updateUser,id);
    }

    @Override
    public void deleteCase(Long id){
        caseDto.deleteCase(id);
    }

    @Override
    public int updateCasePid(Long newPid,Long id){
        return caseDto.updateCasePid(newPid,id);
    }

    @Override
    public int addCase(Long id,Long pId,String caseCode,String caseTitle){
        return caseDto.addCase(id,pId,caseCode,caseTitle);
    }

    @Override
    public CaseEntity selectCaseById(Long id){
        return caseDto.selectCaseById(id);
    }

    @Override
    public Long selectCasePidByCaseId(Long id){
        return caseDto.selectCasePidByCaseId(id);
    }

    @Override
    public List<Long> selectCaseIdByPid(Long pId){
        return caseDto.selectCaseIdByPid(pId);
    }
}
