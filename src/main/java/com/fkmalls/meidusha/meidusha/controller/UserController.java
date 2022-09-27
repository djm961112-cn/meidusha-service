package com.fkmalls.meidusha.meidusha.controller;

import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.auth.LogPrint;
import com.fkmalls.meidusha.meidusha.auth.PassToken;
import com.fkmalls.meidusha.meidusha.auth.UserLoginToken;
import com.fkmalls.meidusha.meidusha.constants.enums.StatusCode;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.entity.UserRoleEntity;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.service.SysLogService;
import com.fkmalls.meidusha.meidusha.service.TokenUtilService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import com.fkmalls.meidusha.meidusha.util.MakeSnowId;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * dengjinming
 * 2022/8/1
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtilService tokenUtilService;

    @Autowired
    UserUtil userUtil;
    @Autowired
    SysLogService sysLogService;

    @PassToken
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @LogPrint
    public Response<?> login(@RequestBody UserEntity userEntity){
        /***
         * 登陆校验用户信息
         */
        List<UserEntity> userEntityList=userService.selectUserByUserNameAndPassword(userEntity.getUserName(),
                userEntity.getPassword());
        if(userEntityList.size()==0){
            return Response.build(StatusCode.LOGIN_ERROR.getStatus(),
                    StatusCode.LOGIN_ERROR.getMessage());
        }else {
            JSONObject data = new JSONObject();
            String token=tokenUtilService.getToken(userEntityList.get(0));
            data.put("token",token);
            userService.updateUserToken(token,userEntity.getUserName());
            userService.fixLoginState(0, userEntity.getUserName());
            return Response.success(data);
        }
    }

    @PassToken
    @RequestMapping(value = "/user/info",method =RequestMethod.GET)
    public Response<?> info(@RequestHeader Map requestHeader) {
        /***
         * 返回用户信息
         */
        String token=((String) requestHeader.get("authorization")).split(" ")[1];
        UserEntity userEntity=userUtil.findUserByTokenUserId(token);
        if (userEntity!=null){
            JSONObject data = new JSONObject();
            data.put("userMessage",userEntity);
            data.put("userRoles","super");
            data.put("userName",userEntity.getUserName());
            data.put("userId",userEntity.getId());
            data.put("refreshToken",token);
            data.put("token",token);
            data.put("password",userEntity.getPassword());
            return Response.success(data);
        }else {
            return Response.build(StatusCode.LOGIN_ERROR.getStatus(),
                    StatusCode.LOGIN_ERROR.getMessage());

        }
    }

    @UserLoginToken
    @RequestMapping(value = "/user/logout",method =RequestMethod.POST)
    public Response<?> logout(@RequestHeader Map requestHeader) {
        /***
         * 退出登录
         */
        log.info("Auto:{}",(String) requestHeader.get("authorization"));
        Long id= tokenUtilService.getTokenUserId(((String) requestHeader.get("authorization")).split(" ")[1]);
        log.info("退出用户id:{}",id);
        if (userService.delUserToken(id)==1 && userService.fixLogOutState(1,id)==1){
            return Response.success();
        }else {
            return Response.build(StatusCode.LOGOUT_ERROR.getStatus(),
                    StatusCode.LOGOUT_ERROR.getMessage());
        }
    }


    @RequestMapping(value = "/user/uploadUserImage", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> uploadHeader(@RequestPart(value = "image") MultipartFile userImage,@RequestHeader Map requestHeader){
        /**
         * 上传用户头像
         */
        //判断图片是否为空
        if (userImage == null) {
            return Response.build(StatusCode.FILE_IMPORT_ERROR.getStatus(),
                    StatusCode.FILE_IMPORT_ERROR.getMessage());
        }
        //判断图片是否合法
        String fileName = userImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!suffix.equals(".png") && !suffix.equals(".jpg") && !suffix.equals(".jpeg")) {
            return Response.build(StatusCode.FILE_IMPORT_ERROR.getStatus(),
                    StatusCode.FILE_IMPORT_ERROR.getMessage());
        }
        //启动本地服务时新生成的文件地址
        //String imageFilePath = "/Volumes/work/ERPCode/testProjrct/MeiDuSha/image";
        //打包到测试服务器的文件地址
        String imageFilePath="/root/meidusha/image";

        File targetFile = new File(imageFilePath, fileName);
        if (!targetFile.getParentFile().exists()) {//注意，判断父级路径是否存在
            targetFile.getParentFile().mkdirs();
        }
        //保存原文件
        try {
            userImage.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.build(StatusCode.FILE_IMPORT_ERROR.getStatus(),
                    StatusCode.FILE_IMPORT_ERROR.getMessage());
        }

        if(((String)requestHeader.get("authorization"))==null || ((String)requestHeader.get("authorization")).equals("")){
            return Response.noAuth();
        }
        Long userId=userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getId();
        String domain="http://121.40.127.239:9100";
    /*
    domain:服务器地址
    */
        String imagePath = domain+"/user/headerImage/" + fileName;
        //更新数据库中的url
        userService.updateUserHeader(imagePath,userId);
        return Response.success();
    }

    @PassToken
    @RequestMapping(value = "/user/headerImage/{fileName}",method =RequestMethod.GET)
    public void getUserImage(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        /**
         * 获取用户头像
         */
        log.info("开始查找{}....",fileName);
        //启动本地服务时新生成的文件地址
        //String imageFilePath = "/Volumes/work/ERPCode/testProjrct/MeiDuSha/image";
        //打包到测试服务器的文件地址
        String imageFilePath="/root/meidusha/image";


        //找到服务器存放路径
        fileName = imageFilePath + "/" + fileName;
        //解析文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")).replace(".","");
        System.out.println("su:"+suffix);
        //响应的格式
        response.setContentType("image/" + suffix);
        byte[] buffer = new byte[1024];
        try (FileInputStream stream = new FileInputStream(fileName);
             ServletOutputStream os = response.getOutputStream();) {

            int b = 0;
            while ((b = stream.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("读取头像失败", e);
        }
        //return buffer;
    }

    @RequestMapping(value = "/getAllUser",method =RequestMethod.GET)
    public Response<?> getUserImage() {
        /**
         * 获取所有已注册的用户
         */
        List<UserEntity> nickUserNameList=userService.findAllUser();

        return Response.successList(nickUserNameList);
    }

    @RequestMapping(value = "/user/List",method = RequestMethod.POST)
    public Response<?> findUserListByRoleId(@RequestParam Long roleId){
        /**
         * 通过角色id查询用户列表
         */
        List<UserEntity> userEntityList=userService.findUserListByRoleId(roleId);
        return Response.successList(userEntityList);
    }

    @RequestMapping(value = "/user/emailsList",method = RequestMethod.GET)
    public Response<?> findEmailsList(){
        /**
         * 查询所有用户的阿里邮箱
         */
        List<UserEntity> userEntityList=userService.findEmailsList();
        return Response.successList(userEntityList);
    }

    @PassToken
    @RequestMapping(value = "/user/registerUser",method = RequestMethod.POST)
    public Response<?> registerUser(@RequestBody UserEntity userEntity){
        /**
         * 注册用户
         */
        MakeSnowId getSnowId = new MakeSnowId(0,0);
        userEntity.setId(getSnowId.nextId());
        userEntity.setCreateUser(userEntity.getUserName());
        if (userService.registerUser(userEntity)==1){
            return Response.success();
        }else {
            return Response.addError();
        }
    }

    @PassToken
    @RequestMapping(value = "/user/fingAllRole",method = RequestMethod.GET)
    public Response<?> fingAllRole(){
        /**
         * 查询所有角色
         */
        List<UserRoleEntity> userRoleEntityList=userService.fingAllRole();
        return Response.successList(userRoleEntityList);
    }

}
