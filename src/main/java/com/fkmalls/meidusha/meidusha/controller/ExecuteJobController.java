package com.fkmalls.meidusha.meidusha.controller;

import com.fkmalls.meidusha.meidusha.auth.MyLog;
import com.fkmalls.meidusha.meidusha.constants.enums.StatusCode;
import com.fkmalls.meidusha.meidusha.entity.CaseEntity;
import com.fkmalls.meidusha.meidusha.entity.ExecuteJobEntity;
import com.fkmalls.meidusha.meidusha.entity.ExecuteJobItemEntity;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.service.CaseService;
import com.fkmalls.meidusha.meidusha.service.ExecuteJobItemService;
import com.fkmalls.meidusha.meidusha.service.ExecuteJobService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import com.fkmalls.meidusha.meidusha.util.CaseUtil;
import com.fkmalls.meidusha.meidusha.util.MakeSnowId;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dengjinming
 * 2022/8/1
 */
@RestController
@Slf4j
public class ExecuteJobController {

    @Autowired
    ExecuteJobService executeJobService;

    @Autowired
    ExecuteJobItemService executeJobItemService;

    @Autowired
    UserService userService;

    @Autowired
    CaseService caseService;

    @Autowired
    UserUtil userUtil;

    @Autowired
    CaseUtil caseUtil;

    @MyLog(value = "新增测试用例执行任务")
    @RequestMapping(value = "/addExecuteJob",method = RequestMethod.POST)
    public Response<?> logout(@RequestBody ExecuteJobEntity executeJobEntity, @RequestHeader Map requestHeader) {
        /***
         * 新增测试用例执行任务
         */
        if (executeJobEntity.getCaseIdList().size()==0){
            return Response.addErrorMsg("请关联至少一条测试用例！");
        }
        String token=((String) requestHeader.get("authorization")).split(" ")[1];
        UserEntity userEntity=userUtil.findUserByTokenUserId(token);
        MakeSnowId getSnowId = new MakeSnowId(0,0);
        Long executeJobId=getSnowId.nextId();
        log.info("executeJobEntity:"+executeJobEntity.toString());
        //组装任务
        executeJobEntity.setId(executeJobId);
        executeJobEntity.setExecuteUserName(userService.findUserByUserId(Long.parseLong(executeJobEntity.getExecuteUser())).get(0).getNickName());
        executeJobEntity.setCreateUser(userEntity.getNickName());
        executeJobEntity.setCaseNumber(executeJobEntity.getCaseIdList().size());
        //塞入数据库
        if (executeJobService.addExecuteJob(executeJobEntity)==1){
            for (Long caseId : executeJobEntity.getCaseIdList()){
                //获取前端传入的case，生成任务明细关联具体用例
                executeJobItemService.addExecuteItem(getSnowId.nextId(),caseId,executeJobId);
            }
            return Response.success();
        }
        return Response.build(StatusCode.ADD_EXECUTE_ERROR.getStatus(),
                StatusCode.ADD_EXECUTE_ERROR.getMessage());

    }

    @RequestMapping(value = "/getExecuteJobList",method =RequestMethod.POST)
    public Response<?> getExecuteJobList(@RequestBody ExecuteJobEntity executeJobEntity) {
        /***
         * 查询测试用例执行任务列表
         */

        List<ExecuteJobEntity> ExecuteJobEntityList = executeJobService.selectExecuteJobEntity(executeJobEntity);
        return Response.successList(ExecuteJobEntityList);

    }

    public static Map<Long, CaseEntity> map = new HashMap<>();


    @RequestMapping(value = "/getExecuteJobCaseList",method =RequestMethod.POST)
    public Response<?> getExecuteJobCaseList(@RequestParam Long executeJobId) {
        /***
         * 查询用例执行任务相关的测试用例列表
         */
        //获取执行任务相关的测试用例id
        List<ExecuteJobItemEntity> executeJobItemEntityList=executeJobItemService.selectExecuteJobItemByExecuteId(executeJobId);
        List<Long> caseIdListInit=new ArrayList<>();
        for (ExecuteJobItemEntity executeJobEntity : executeJobItemEntityList){
            caseIdListInit.add(executeJobEntity.getCaseId());
        }
        //循环获取上面id列表所有的casePId并去重
        List<Long> caseIdAndPidList=caseUtil.duplicateListBySet(caseUtil.getCaseIdAndPidList(caseIdListInit));
        //通过caseId查出case对象放进列表
        List<CaseEntity> caseEntityList= new ArrayList<>();
        for (Long id:caseIdAndPidList){
            CaseEntity caseEntity=caseService.selectCaseById(id);
            caseEntityList.add(caseEntity);
        }
        //将原始关联的case列表与最终的case列表比对，将原始的caseStatus赋值给最新的
        caseUtil.equalsAndSetCaseStatus(executeJobItemEntityList,caseEntityList);
        return Response.build(StatusCode.SERVICE_RUN_SUCCESS.getStatus(),
                StatusCode.SERVICE_RUN_SUCCESS.getMessage(),
                caseEntityList,
                caseEntityList.size());
    }

    @MyLog(value = "执行测试用例")
    @RequestMapping(value = "/executeCase",method =RequestMethod.POST)
    public Response<?> executeCase(@RequestParam String caseId,int caseStatus,Long executeJobId) {
        /***
         * 执行测试用例
         */
        if (executeJobItemService.executeCase(caseStatus,Long.parseLong(caseId),executeJobId)==1){
            return Response.success();
        }else {
            return Response.build(StatusCode.UPDATE_EXECUTE_CASE_STATUS_ERROR.getStatus(),
                    StatusCode.UPDATE_EXECUTE_CASE_STATUS_ERROR.getMessage());
        }
    }
}
