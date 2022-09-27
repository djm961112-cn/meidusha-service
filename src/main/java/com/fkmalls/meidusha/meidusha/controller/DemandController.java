package com.fkmalls.meidusha.meidusha.controller;

import com.fkmalls.meidusha.meidusha.auth.MyLog;
import com.fkmalls.meidusha.meidusha.entity.demand.DemandEntity;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.service.DingDing.DingService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import com.fkmalls.meidusha.meidusha.service.demand.DemandService;
import com.fkmalls.meidusha.meidusha.util.MakeSnowId;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * dengjinming
 * 2022/8/19
 */
@RestController
@Slf4j
@RequestMapping(value = "/demand")
public class DemandController {

    @Autowired
    DemandService demandService;
    @Autowired
    UserUtil userUtil;
    @Autowired
    DingService dingService;
    @Autowired
    UserService userService;

    @MyLog(value = "新增需求")
    @RequestMapping(value = "/addDemand",method = RequestMethod.POST)
    public Response<?> addDemand(@RequestBody DemandEntity demandEntity, @RequestHeader Map requestHeader){
        /**
         * 新增需求
         */
        if (demandEntity.getDemandName()==null || demandEntity.getDemandName()==""){
            return Response.addErrorMsg("缺少需求名称！");
        }
        String userName=userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getUserName();
        MakeSnowId getSnowId = new MakeSnowId(0,0);
        if (demandService.addDemand(
                getSnowId.nextId(),
                demandEntity.getDemandName(),
                demandEntity.getDemandAddress(),
                demandEntity.getProductId(),
                demandEntity.getQaId(),
                demandEntity.getDevId(),
                demandEntity.getFrontId(),
                userName
        )==1){
            return Response.success();
        }else {
            log.info("RequestDemandEntity:{}",demandEntity);
            return Response.addError();
        }
    }

    @RequestMapping(value = "/selectAllDemand",method = RequestMethod.POST)
    public Response<?> selectAllDemand(@RequestBody DemandEntity demandEntity){
        //@RequestBody int pageSize,@RequestBody int pageNum
        /**
         * 查询所有未删除的需求
         */
        List<DemandEntity> demandEntityList=demandService.selectAllDemand(demandEntity);
        return Response.successList(demandEntityList);
    }

    @MyLog(value = "修改了需求进度")
    @RequestMapping(value = "/updateDemandStatus",method = RequestMethod.POST)
    public Response<?> updateDemandStatus(@RequestBody DemandEntity demandEntity, @RequestHeader Map requestHeader){
        /**
         * 修改需求进度
         */
        String updateUser=userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getNickName();
        if (demandService.updateDemandStatusById(demandEntity.getDemandStatus(),updateUser,demandEntity.getId())==1){
            dingService.makeUpDingMessage(demandEntity,updateUser);
            return Response.success();
        }else {
            return Response.fixError();
        }
    }

}
