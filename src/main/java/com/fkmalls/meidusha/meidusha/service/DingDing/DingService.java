package com.fkmalls.meidusha.meidusha.service.DingDing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.constants.enums.DemandStatus;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.demand.DemandEntity;
import com.fkmalls.meidusha.meidusha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * dengjinming
 * 2022/9/1
 */
@Service
@Slf4j
public class DingService {

    @Autowired
    UserService userService;

    //请求地址以及access_token
    private String Webhook = "https://oapi.dingtalk.com/robot/send?access_token=4a45b2bed60b552445c93f2dc3448515b41abb45d86a2a15f657a4168d95c5d6";
    //密钥
    private String secret = "SEC94fb36debdfeb3f1c41761b47bbf9d335edec571bba6223bba33dd8d54b37572";

    public String encode() throws Exception {
        /**
         * 生成时间戳和验证信息
         */
        //获取时间戳
        Long timestamp = System.currentTimeMillis();
        //把时间戳和密钥拼接成字符串，中间加入一个换行符
        String stringToSign = timestamp + "\n" + this.secret;
        //声明一个Mac对象，用来操作字符串
        Mac mac = Mac.getInstance("HmacSHA256");
        //初始化，设置Mac对象操作的字符串是UTF-8类型，加密方式是SHA256
        mac.init(new SecretKeySpec(this.secret.getBytes("UTF-8"), "HmacSHA256"));
        //把字符串转化成字节形式
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        //新建一个Base64编码对象
        Base64.Encoder encoder = Base64.getEncoder();
        //把上面的字符串进行Base64加密后再进行URL编码
        String sign = URLEncoder.encode(new String(encoder.encodeToString(signData)), "UTF-8");
        String result = "&timestamp=" + timestamp + "&sign=" + sign;
        return result;
    };

    public void dingRequest(String message, List<String> mobileList) {
        /**
         * 把传入的message发送给钉钉机器人
         */
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = null;
        try {
            url = this.Webhook + this.encode();
            //System.out.println("url:"+url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(url);
        //设置http的请求头，发送json字符串，编码UTF-8
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //生成json对象传入字符
        JSONObject result = new JSONObject();
        JSONObject text = new JSONObject();
        JSONObject at = new JSONObject();
        if (mobileList.size()!=0){
            List<String> m=new ArrayList<>();
            for (String mobile:mobileList){
                m.add(mobile);
            }
            at.put("atMobiles",m);
        }
        //System.out.println("mobile:"+at);
        text.put("content", message);
        result.put("text", text);
        result.put("msgtype", "text");
        result.put("at",at);
        String jsonString = JSON.toJSONString(result);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        //设置http请求的内容
        httpPost.setEntity(entity);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
        // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            //System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                log.info("请求钉钉通知url:{}\n@用户:{}\n响应状态为:{}\n响应内容长度为:{}\n响应内容为:{}\n",
                        url,
                        at,
                        response.getStatusLine(),
                        responseEntity.getContentLength(),
                        EntityUtils.toString(responseEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void makeUpDingMessage(DemandEntity demandEntity, String updateUser){
        /**
         * 组合钉钉通知信息,并通知
         */
        String message = "";
        switch (demandEntity.getDemandStatus()){
            case 10:message = DemandStatus.DEVELOPMENT.getMessage();break;
            case 20:message = DemandStatus.TESTED.getMessage();break;
            case 30:message = DemandStatus.NOTTEST.getMessage();break;
            case 40:message = DemandStatus.TEST_SUCCESS.getMessage();break;
            case 50:message = DemandStatus.RELEASE.getMessage();break;
        }
        List<String> mobileList=new ArrayList<>();
        String at="";
        String productMobile="",qaMobile="",devMobile="",frontMobile="";
        if (demandEntity.getProductId()!=null){
            if (demandEntity.getDemandStatus() == 40 || demandEntity.getDemandStatus() == 50){
                List<UserEntity> userEntityList=userService.findUserByUserId(demandEntity.getProductId());
                if (userEntityList.size()!=0){
                    productMobile=userEntityList.get(0).getDingMobile();
                    mobileList.add(productMobile);
                    at+="@"+productMobile;
                }
            }
        }
        if (demandEntity.getQaId()!=null && demandEntity.getDemandStatus() == 20){
            qaMobile=userService.findUserByUserId(demandEntity.getQaId()).get(0).getDingMobile();
            mobileList.add(qaMobile);
            at+="@"+qaMobile;
        }
        if (demandEntity.getDevId()!=null){
            if (demandEntity.getDemandStatus() == 30 || demandEntity.getDemandStatus() == 40){
                devMobile=userService.findUserByUserId(demandEntity.getDevId()).get(0).getDingMobile();
                mobileList.add(devMobile);
                at+="@"+devMobile;
            }

        }
        if (demandEntity.getFrontId()!=null ){
            if (demandEntity.getDemandStatus() == 30 || demandEntity.getDemandStatus() == 40){
                frontMobile=userService.findUserByUserId(demandEntity.getFrontId()).get(0).getDingMobile();
                mobileList.add(frontMobile);
                at+="@"+frontMobile;
            }

        }
        log.info("mobileList:{}",mobileList);
        //System.out.println("at:"+at);
        dingRequest("需求："+demandEntity.getDemandName()+"\n"+
                        "更新状态为:"+message+"\n"+
                        "操作人："+updateUser+"\n"+
                        "操作时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n"+
                        at,
                mobileList);
    }

}