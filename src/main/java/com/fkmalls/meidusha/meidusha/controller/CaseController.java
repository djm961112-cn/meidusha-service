package com.fkmalls.meidusha.meidusha.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.auth.MyLog;
import com.fkmalls.meidusha.meidusha.auth.UserLoginToken;
import com.fkmalls.meidusha.meidusha.constants.enums.StatusCode;
import com.fkmalls.meidusha.meidusha.entity.CaseEntity;
import com.fkmalls.meidusha.meidusha.entity.XmindEntity;
import com.fkmalls.meidusha.meidusha.entity.response.controller.Response;
import com.fkmalls.meidusha.meidusha.service.CaseService;
import com.fkmalls.meidusha.meidusha.service.TokenUtilService;
import com.fkmalls.meidusha.meidusha.service.UserService;
import com.fkmalls.meidusha.meidusha.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * create by 金明 on 2022/6/1.
 */
@RestController
@Slf4j
public class CaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    MakeCaseCode makeCaseCode;

    @Autowired
    TokenUtilService tokenUtilService;

    @Autowired
    UserService userService;

    @Autowired
    UserUtil userUtil;
    @Autowired
    CaseUtil caseUtil;

    @UserLoginToken
    @GetMapping(value = "/getCaseList")
    public Response<?> getCaseListNew() {
        /**
        * 获取测试用例list
        * */
        List<CaseEntity> caseList = caseService.getCaseList();
        return Response.successList(caseList);
    }

    @MyLog(value = "导入了xmind文件")
    @UserLoginToken
    @RequestMapping(value = "/uploadXmind", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> uploadImageFile(@RequestPart(value = "file") MultipartFile uploadXmind) {
        /**
        * 导入xmind文件
        * */
        try {
            if (uploadXmind == null) {
                String data="";
                return Response.build(StatusCode.NOT_FOUND_ENTITY.getStatus(),
                        StatusCode.NOT_FOUND_ENTITY.getMessage(),
                        data,data.length());
            }
            String fileName=uploadXmind.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀，用以识别哪种格式数据
            if (suffix.equals(".xmind")) {
                //todo:上传服务器需要更改路径
                //启动本地服务时新生成的文件地址
                //String xmindFilePath = "/Volumes/work/ERPCode/testProjrct/MeiDuSha/file";
                //打包到测试服务器的文件地址
                String xmindFilePath="/home/fkmalls/fkmalls-meidusha/file";

                File targetFile = new File(xmindFilePath, fileName);
                if (!targetFile.getParentFile().exists()) {//注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }
                //保存
                uploadXmind.transferTo(targetFile);

                //解压xmind
                Long time = System.currentTimeMillis();
                String desc = xmindFilePath +"/"+fileName.split("\\.")[0] + "_" + time.toString() + "/";
                if (!FileUtil.decompressZip(targetFile.getPath(),desc)) {
                    log.info("解析失败");
                    return Response.build(StatusCode.FILE_IMPORT_ERROR.getStatus(),StatusCode.FILE_IMPORT_ERROR.getMessage());
                }

                //解析xmind
                File jsonFile = new File((desc + "content.json").replace("/", File.separator));
                log.info("[jsonFile是否存在]" + jsonFile.exists());
                String jsonPath= jsonFile.getPath();
                String jsonstring= JsonUtil.getDataFromJsonFile(jsonPath);
                JSONArray parseArray = JSONObject.parseArray(jsonstring);
                JSONObject getObj = null;
                try {
                    getObj = parseArray.getJSONObject(0);
                } catch (Exception e) {
                    log.info("找不到解压后的content.json文件："+e);
                    e.printStackTrace();
                    return Response.fileServiceError();
                }
                JSONObject rootTopic = getObj.getJSONObject("rootTopic");

                //存储解析出的json内容
                fetchCase(rootTopic.toJSONString(), null);
                //TODO:
                String data="";
                return Response.success(data);
            }
            log.error("系统异常，上传文件格式非法");
            String data="";
            return Response.build(StatusCode.FILE_FORMAT_ERROR.getStatus(),
                    StatusCode.FILE_FORMAT_ERROR.getMessage(),
                    data,data.length());//500,系统异常
        } catch (Exception e) {
            log.error("系统异常，上传文件失败,msg={}", e.getMessage());
            e.printStackTrace();
            String data="";
            return Response.serviceBusy(data);//500,系统繁忙
        }


    }

    private void fetchCase(String parentCaseJson,CaseEntity parentCaseEntity){
        XmindEntity xmindEntity = JSON.parseObject(parentCaseJson, XmindEntity.class);
        if(xmindEntity.getTitle()==null || xmindEntity.getTitle().equals("")){return;}
        MakeSnowId getSnowId = new MakeSnowId(0,0);
        CaseEntity caseEntity = CaseEntity.builder()
                .caseTitle(xmindEntity.getTitle())
                //.caseCondition(xmindEntity.getTitle())//将title放进前置条件中，目前先做简单版本，不判断title属于前置条件还是标题，全部放入标题中
                .pId(Objects.isNull(parentCaseEntity)?null:parentCaseEntity.getId())
                .id(getSnowId.nextId())
                .build();
        //生成CaseCode并放入caseEntity
        caseEntity.setCaseCode(makeCaseCode.makeNewCaseCode());
        //save caseEntity
        caseService.saveCaseWithoutCaseCondition(
                caseEntity.getId(),
                caseEntity.getPId(),
                caseEntity.getCaseCode(),
                caseEntity.getCaseTitle(),
                caseEntity.getCaseTag(),
                caseEntity.getCaseDelTag()
                );
        System.out.println(xmindEntity.getChildren());
        if (xmindEntity.getChildren()==null || xmindEntity.getChildren().equals("{}")){
            return;
        }
        String childrenJson = JSONObject.parseObject(xmindEntity.getChildren()).getString("attached");
        List<XmindEntity> xmindChildrensList = JSON.parseArray(childrenJson, XmindEntity.class);
        /*if (CollectionUtils.isEmpty(xmindChildrensList)) {
            return;
        }*/

        //TODO：用于判断是否是用例层，用以区分不同层不同存储方式
        /*boolean isCaseLevel = false;
        for (XmindEntity xe : xmindChildrens) {
            if (xe.getMarkers()==null){
                return;
            }else if (xe.getMarkers().contains(XmindMarkers.CASE_TITLE.getMarkersId())) {//判断是否是用例层
                isCaseLevel = true;
            }
        }
        if (isCaseLevel) {//判断是否是用例层
            caseService.saveCaseWithoutCaseCondition(
                    caseEntity.getPId(),
                    caseEntity.getCaseCode(),
                    caseEntity.getCaseTitle(),
                    caseEntity.getCaseTag(),
                    caseEntity.getCaseDelTag()
            );
            return;
        }*/
        //

        for (XmindEntity xe : xmindChildrensList) {
            log.info(xe.toString());
            fetchCase(JSON.toJSONString(xe), caseEntity);
        }
    }

    @MyLog(value = "修改了测试用例")
    @UserLoginToken
    @RequestMapping(value = "/fixCase", method = RequestMethod.POST)
    public Response<?> fixCase(@RequestBody CaseEntity caseEntity,@RequestHeader Map requestHeader) {
        /**
         * 修改测试用例
         * */
        if (caseEntity.getCaseCondition()!=null){
            caseEntity.setCaseCondition(caseEntity.getCaseCondition().replaceAll("\\\\", "\\\\\\\\"));
        }
        if (caseEntity.getCaseExpectedResults()!=null){
            caseEntity.setCaseExpectedResults(caseEntity.getCaseExpectedResults().replaceAll("\\\\", "\\\\\\\\"));
        }
        if (caseEntity.getCaseOperationSteps()!=null){
            caseEntity.setCaseOperationSteps(caseEntity.getCaseOperationSteps().replaceAll("\\\\", "\\\\\\\\"));
        }
        if(((String)requestHeader.get("authorization"))==null || ((String)requestHeader.get("authorization")).equals("")){
            return Response.noAuth();
        }
         if(caseService.fixCase(caseEntity.getCaseCode(),
                 caseEntity.getCaseTitle(),
                 caseEntity.getCaseCondition(),
                 caseEntity.getCaseExpectedResults(),
                 caseEntity.getCaseActuallyResults(),
                 caseEntity.getCaseOperationSteps(),
                 userUtil.findUserByTokenUserId(((String)requestHeader.get("authorization")).split(" ")[1]).getUserName(),
                 caseEntity.getId()
                 )==1){
             return Response.success();
         }else {
             String data="修改失败！";
             return Response.build(StatusCode.FIX_ERROR.getStatus(),
                     StatusCode.FIX_ERROR.getMessage(),
                     data);
         }
    }

    @MyLog(value = "拖拽了测试用例")
    @RequestMapping(value = "/updateCasePid", method = RequestMethod.POST)
    public Response<?> deleteCase(@RequestParam Long id,Long newPid) {
        /**
         * 拖拽测试用例
         * */
        if(caseService.updateCasePid(newPid,id)==1){
            return Response.success();
        }else {
            String data="拖拽失败！";
            return Response.serviceBusy(data);
        }
    }

    @MyLog(value = "删除了测试用例")
    @RequestMapping(value = "/deleteCase", method = RequestMethod.POST)
    public Response<?> deleteCase(@RequestParam Long id) {
        /**
         * 删除单个测试用例
         * */
        List<Long> caseIdList=caseUtil.getAllIdByPid(id);
        for (Long pId : caseIdList){
            caseService.deleteCase(pId);
        }
        return Response.success();
    }

    @MyLog(value = "新增了测试用例")
    @RequestMapping(value = "/addCase",method =RequestMethod.POST)
    public Response<?> logout(@RequestBody CaseEntity caseEntity) {
        /***
         * 新增测试用例
         */
        if (caseEntity.getPId()==null || caseEntity.getCaseTitle()==null){
            return Response.build(StatusCode.ADD_CASE_ERROR.getStatus(),
                    StatusCode.ADD_CASE_ERROR.getMessage());
        }else {
            MakeSnowId getSnowId = new MakeSnowId(0,0);
            CaseEntity addCaseEntity = CaseEntity.builder()
                    .id(getSnowId.nextId())
                    .pId(caseEntity.getPId())
                    .caseTitle(caseEntity.getCaseTitle())
                    .build();
            addCaseEntity.setCaseCode(makeCaseCode.makeNewCaseCode());
            if (caseService.addCase(addCaseEntity.getId(),
                    addCaseEntity.getPId(),
                    addCaseEntity.getCaseCode(),
                    addCaseEntity.getCaseTitle())==1){
                return Response.success();
            }else {
                return Response.build(StatusCode.LOGIN_ERROR.getStatus(),
                        StatusCode.LOGIN_ERROR.getMessage());
            }
        }
    }
}