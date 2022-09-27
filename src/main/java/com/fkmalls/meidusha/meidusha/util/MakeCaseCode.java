package com.fkmalls.meidusha.meidusha.util;

import com.fkmalls.meidusha.meidusha.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 */
@Component
public class MakeCaseCode {
    /**
     * create by 金明 on 2022/07/08
     * 生成caseCode
     */
    @Autowired
    public CaseService caseService;

    public String makeNewCaseCode() {
        /*
         * 获取年月日
         * */
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        //2022-03-17 01:10:18
        //0123456789012345678
        String year_ar=dateString.substring(2,4);
        String month=dateString.substring(5,7);
        String date=dateString.substring(8,10);
        String codeDate=year_ar+month+date;
        /*
         * 获取最新的code来判断新增的code规则
         * */
        String waterNum="0001";
        String code=caseService.findLastCode();
        if(code!=null){
            String newCodeDate=code.substring(4,10);
            if(codeDate.equals(newCodeDate)){
                Integer codeWaterNum=new Integer(code.substring(10));
                Integer intWaterNUm=codeWaterNum+1;
                if (intWaterNUm < 10) {
                    waterNum="000"+String.valueOf(intWaterNUm);
                }else if (intWaterNUm<100){
                    waterNum="00"+String.valueOf(intWaterNUm);
                }else if (intWaterNUm<1000){
                    waterNum="0"+String.valueOf(intWaterNUm);
                }else {
                    waterNum=String.valueOf(intWaterNUm);
                }
            }
        }
        return "CASE"+codeDate+waterNum;
    }
}
