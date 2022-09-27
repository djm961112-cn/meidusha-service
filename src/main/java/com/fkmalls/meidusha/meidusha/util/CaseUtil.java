package com.fkmalls.meidusha.meidusha.util;

import com.fkmalls.meidusha.meidusha.entity.CaseEntity;
import com.fkmalls.meidusha.meidusha.entity.ExecuteJobItemEntity;
import com.fkmalls.meidusha.meidusha.service.CaseService;
import com.fkmalls.meidusha.meidusha.service.ExecuteJobItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * dengjinming
 * 2022/8/15
 */
@Component
@Slf4j
public class CaseUtil {
    @Autowired
    ExecuteJobItemService executeJobItemService;

    @Autowired
    CaseService caseService;


    public Map getCaseStatusByExecuteJobId(Long executedId,int caseNumber){
        log.info("caseNumber:{}",caseNumber);
        int success = 0,fails=0,suspend=0,unexecuted=0;
        for (ExecuteJobItemEntity executeJobItemEntity : executeJobItemService.selectExecuteJobItemByExecuteId(executedId)){
            if (executeJobItemEntity.getCaseStatus()==0){
                unexecuted++;
            }else if (executeJobItemEntity.getCaseStatus()==10){
                success++;
            }else if (executeJobItemEntity.getCaseStatus()==20){
                fails++;
            }else if (executeJobItemEntity.getCaseStatus()==30){
                suspend++;
            }
        }
        Map caseStatus=new HashMap();
        caseStatus.put("success",success);
        caseStatus.put("fails",fails);
        caseStatus.put("suspend",suspend);
        caseStatus.put("unexecuted",(caseNumber-success-fails-suspend));
        log.info("unexecuted:{},new:{}",unexecuted,caseNumber-success-fails-suspend);
        return caseStatus;
    }

    List<Long> caseIdListSta=new ArrayList<>();
    public List<Long> getCaseIdAndPidList(List<Long> caseIdList){
        /**
         * 获取列表caseId对应所有Pid及自己
         */
        log.info("需要遍历的列表大小为{}",caseIdList.size());
        List<Long> newReturnList=caseIdList;
        List<Long> append = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        //append.addAll(caseIdList);
        for (Long caseId:caseIdList){//遍历入参的caseId列表
            tempList=findAllPidAndId(caseId);
            append.addAll(tempList);
            log.info("叶子节点为{}的值，遍历出的结果为{}",caseId,tempList);
        }
        caseIdListSta.clear();//遍历结束后，初始化caseIdListSta列表
        newReturnList.addAll(append);
        return newReturnList;
    }


    public List<Long> findAllPidAndId(Long id){
        /**
         * 向上遍历Pid，直到pid为null为止
         */
        Long casePid=caseService.selectCasePidByCaseId(id);
        if (casePid==null){
            return caseIdListSta;
        }else {
            caseIdListSta.add(casePid);
            return findAllPidAndId(casePid);
        }
    }

    List<Long> initList = new ArrayList<>();
    List<Long> initList2 = new ArrayList<>();
    public List<Long> getAllIdByPid(Long pId){
        /**
         * 获取所有子节点id
         */
        List<Long> returnList=new ArrayList<>();
        initList.add(pId);
        findAllIdByPid(initList,initList2);
        returnList.addAll(initList2);
        initList.clear();
        initList2.clear();
        return returnList;
    }

    public void findAllIdByPid(List<Long> listId,List<Long> all){
        /**
         * 向下遍历所有id
         */
        if (listId.size()>0){
            Iterator<Long> iterator = listId.iterator();
            while (iterator.hasNext()) {
                Long id = iterator.next();
                List<Long> ids= caseService.selectCaseIdByPid(id);
                all.addAll(ids);
                findAllIdByPid(ids,all);
            }
        }
    }

    //去除List列表中重复值：使用set进行去重
    public static List<Long> duplicateListBySet(List<Long> list) {
        HashSet h = new HashSet(list);
        List newList = new ArrayList();
        newList.addAll(h);
        return newList;
    }

    //
    public void equalsAndSetCaseStatus(List<ExecuteJobItemEntity> executeJobItemEntityList,List<CaseEntity> caseEntityList){
        for (ExecuteJobItemEntity executeJobItemEntity: executeJobItemEntityList){
            Long id=executeJobItemEntity.getCaseId();
            for (CaseEntity caseEntity: caseEntityList){
                if (id.equals(caseEntity.getId())){
                    caseEntity.setCaseStatus(executeJobItemEntity.getCaseStatus());
                }
            }
        }
    }
}
