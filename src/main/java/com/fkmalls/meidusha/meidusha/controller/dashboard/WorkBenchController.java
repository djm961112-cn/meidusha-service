package com.fkmalls.meidusha.meidusha.controller.dashboard;

import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.auth.PassToken;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.service.SysLogService;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * dengjinming
 * 2022/9/16
 * 工作台
 */
@RestController
@Slf4j
@RequestMapping(value = "/dashboard")
public class WorkBenchController {

    @Autowired
    SysLogService sysLogService;
    @Autowired
    UserUtil userUtil;

    @RequestMapping(value = "/logList",method = RequestMethod.GET)
    public Response<?> logList() {
        /***
         * 查询所有日志
         */
        return Response.successList(sysLogService.findAllLog());
    }

    @PassToken
    @RequestMapping(value = "/getTitle",method = RequestMethod.GET)
    public Response<?> getTitle() {
        /***
         * 获取随机励志短句
         */
        JSONObject data=new JSONObject();
        data.put("title",userUtil.randomTitle());
        return Response.success(data);
    }
}
