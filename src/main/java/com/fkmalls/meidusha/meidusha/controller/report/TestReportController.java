package com.fkmalls.meidusha.meidusha.controller.report;

import com.fkmalls.meidusha.meidusha.auth.MyLog;
import com.fkmalls.meidusha.meidusha.entity.email.MailRequest;
import com.fkmalls.meidusha.meidusha.entity.email.SendMailService;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestExecuteMessageEntity;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestProjectEntity;
import com.fkmalls.meidusha.meidusha.entity.testReport.TestReportEntity;
import com.fkmalls.meidusha.meidusha.service.UserService;
import com.fkmalls.meidusha.meidusha.service.demand.DemandService;
import com.fkmalls.meidusha.meidusha.service.testReport.TestExecuteMessageService;
import com.fkmalls.meidusha.meidusha.service.testReport.TestProjectService;
import com.fkmalls.meidusha.meidusha.service.testReport.TestReportService;
import com.fkmalls.meidusha.meidusha.util.MakeSnowId;
import com.fkmalls.meidusha.meidusha.util.MessageUtil;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * dengjinming
 * 2022/8/18
 * 测试报告控制层
 */
@RestController
@Slf4j
@RequestMapping(value = "/testReport")
public class TestReportController {
    @Autowired
    TestExecuteMessageService testExecuteMessageService;
    @Autowired
    TestReportService testReportService;
    @Autowired
    UserUtil userUtil;
    @Autowired
    TestProjectService testProjectService;
    @Autowired
    DemandService demandService;
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    MessageUtil messageUtil;
    @Autowired
    UserService userService;

    @MyLog(value = "新增测试报告")
    @RequestMapping(value = "/addTestReport",method = RequestMethod.POST)
    public Response<?> addTestReport(HttpServletRequest request, @RequestBody TestReportEntity testReportEntity, @RequestHeader Map requestHeader){
        /**
         * 新增测试报告
         */
        log.info("request:{}",request.toString());
        MakeSnowId getSnowId = new MakeSnowId(0,0);
        testReportEntity.setId(getSnowId.nextId());
        String userName=userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getUserName();
        testReportEntity.setCreateUser(userName);
        testReportEntity.setTestUserName(userService.findUserByUserId(testReportEntity.getTestUser()).get(0).getNickName());
        String emailString="";
        for (String emil:testReportEntity.getEmailsList()){
            emailString+=(","+emil);
        }
        if (emailString.indexOf(",")==0){
            emailString=emailString.substring(1);
        }
        testReportEntity.setSendToAliEmails(emailString);
        //添加测试报告
        if (testReportService.addTestReport(testReportEntity)==1){
            //修改需求对应测试报告状态
            demandService.updateReportStatusById(1,testReportEntity.getDemandId());
            //添加测试执行信息
            for (TestExecuteMessageEntity testExecuteMessageEntity : testReportEntity.getTestExecuteMessageEntityList()){
                testExecuteMessageEntity.setId(getSnowId.nextId());
                testExecuteMessageEntity.setTestReportId(testReportEntity.getId());
                testExecuteMessageEntity.setCreateUser(userName);
                testExecuteMessageService.addTestExecuteMessage(testExecuteMessageEntity);
            }
            //添加测试项目信息
            for (TestProjectEntity testProjectEntity : testReportEntity.getTestProjectEntityList()){
                testProjectEntity.setId(getSnowId.nextId());
                testProjectEntity.setTestReportId(testReportEntity.getId());
                testProjectService.addTestProject(testProjectEntity);
            }
            return Response.success();
        }else {
            return Response.addError();
        }
    }

    @MyLog(value = "编辑测试报告")
    @RequestMapping(value = "/editTestReport",method = RequestMethod.POST)
    public Response<?> editTestReport(@RequestBody TestReportEntity testReportEntity, @RequestHeader Map requestHeader){
        /**
         * 编辑测试报告
         */
        String userName=userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getUserName();
        testReportEntity.setUpdateUser(userName);
        String emailString="";
        for (String emil:testReportEntity.getEmailsList()){
            emailString+=(","+emil);
        }
        if (emailString.indexOf(",")==0){
            emailString=emailString.substring(1);
        }
        testReportEntity.setSendToAliEmails(emailString);
        testReportEntity.setTestUserName(userService.findUserByUserId(testReportEntity.getTestUser()).get(0).getNickName());
        if (testReportService.updateTestReport(testReportEntity)==1){
            for (TestExecuteMessageEntity testExecuteMessageEntity : testReportEntity.getTestExecuteMessageEntityList()){
                testExecuteMessageEntity.setCreateUser(userName);
                testExecuteMessageService.updateTestExecuteMsgByTestReportId(testExecuteMessageEntity);
            }
            //添加测试项目信息
            for (TestProjectEntity testProjectEntity : testReportEntity.getTestProjectEntityList()){
                testProjectService.updateTestProject(testProjectEntity);
            }
            return Response.success();
        }else {
            return Response.fixError();
        }
    }

    @RequestMapping(value = "/selectAllTestReport",method = RequestMethod.POST)
    public Response<?> selectAllTestReport(@RequestBody TestReportEntity testReportEntity){
        /**
         * 查询所有测试报告
         */
        List<TestReportEntity> testReportEntityList = testReportService.selectAllTestReport(testReportEntity);
        return Response.successList(testReportEntityList);
    }

    @RequestMapping(value = "/selectTestReport",method = RequestMethod.POST)
    public Response<?> selectTestReport(@RequestParam Long reportId){
        /**
         * 查询测试报告详情
         */
        return Response.success(testReportService.selectTestReport(reportId));
    }

    @MyLog(value = "发送测试报告邮件")
    @RequestMapping(value = "/sendTestReport",method = RequestMethod.POST)
    public Response<?> selectTestReport(@RequestBody TestReportEntity testReportEntity){
        /**
         * 发送测试报告邮件
         */
        if (testReportEntity.getEmailsList()==null){
            return Response.serviceBusy("测试报告发送邮箱不能为空！");
        }
        String emailString="";
        for (String emil:testReportEntity.getEmailsList()){
            emailString+=(","+emil);
        }
        if (emailString.indexOf(",")==0){
            emailString=emailString.substring(1);
        }
        String text= messageUtil.buildMessageText(testReportEntity);
        MailRequest mailRequest =new MailRequest();
        mailRequest.setSendTo(emailString);
        mailRequest.setSubject(testReportEntity.getDemandName()+" 测试报告");
        mailRequest.setText(text);
        sendMailService.sendHtmlMail(mailRequest);
        testReportService.updateSendEmailStatus(testReportEntity);
        return Response.success();
    }
}
