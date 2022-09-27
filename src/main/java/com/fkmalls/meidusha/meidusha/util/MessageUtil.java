package com.fkmalls.meidusha.meidusha.util;

import com.fkmalls.meidusha.meidusha.entity.testReport.TestReportEntity;
import org.springframework.stereotype.Component;

/**
 * dengjinming
 * 2022/9/7
 */
@Component
public class MessageUtil {
    private String modle="<!doctype html><html dir=\\\"ltr\\\" lang=\\\"zh\\\">" +
            "<head><meta charset=\\\"utf-8\\\"><title>测试报告</title><style></style></head>" +
            "<body><div class=\\\"father\\\"><div class=\\\"title\\\">" +
            "<h1 style=\\\"text-align: center;\\\">测试报告</h1>" +
            "</div><div class=\\\"testMessage\\\">" +
            "<h3>测试基本信息</h3>" +
            "<table>" +
            "<tr><th>测试人:</th><tr><th>关联需求:</th></tr>" +
            "<tr><th>测试结论:</th></tr><tr><th>关联测试单:</th></tr>" +
            "<tr><th>测试起止时间:</th></tr><tr><th>测试说明:</th></tr>" +
            "<tr><th>风险说明:</th></tr><tr><th>测试范围:</th></tr></tr></table></div></div></body></html>";

    public String buildMessageText(TestReportEntity testReportEntity){
        modle=modle
                .replace("测试报告</h1>",testReportEntity.getDemandName()+"测试报告</h1>")
                .replace("测试人:</th>","测试人:</th>"+"<th>"+testReportEntity.getTestUserName()+"</th>")
                .replace("关联需求:</th>","关联需求:</th>"+"<th>"+testReportEntity.getDemandName()+"</th>")
                .replace("测试结论:</th>","测试结论:</th>"+"<th>"+testReportEntity.getTestConclusion()+"</th>")
                .replace("关联测试单:</th>","关联测试单:</th>"+"<th>"+testReportEntity.getTestPlan()+"</th>")
                .replace("测试起止时间:</th>","测试起止时间:</th>"+"<th>"+testReportEntity.getTestStartTime()+"~"+testReportEntity.getTestEndTime()+"</th>")
                .replace("测试说明:</th>","测试说明:</th>"+"<th>"+testReportEntity.getTestExplain()+"</th>")
                .replace("风险说明:</th>","风险说明:</th>"+"<th>"+testReportEntity.getRiskExplain()+"</th>")
                .replace("测试范围:</th>","测试范围:</th>"+"<th>"+testReportEntity.getTestRange()+"</th>");
        return modle;
    }
}
