package com.fkmalls.meidusha.meidusha.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fkmalls.meidusha.meidusha.auth.LogPrint;
import com.fkmalls.meidusha.meidusha.auth.MyLog;
import com.fkmalls.meidusha.meidusha.entity.SysLog;
import com.fkmalls.meidusha.meidusha.entity.UserEntity;
import com.fkmalls.meidusha.meidusha.service.SysLogService;
import com.fkmalls.meidusha.meidusha.util.DateUtil;
import com.fkmalls.meidusha.meidusha.util.MakeSnowId;
import com.fkmalls.meidusha.meidusha.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    SysLogService sysLogService;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    UserUtil userUtil;

    SysLog sysLog = new SysLog();

    List<String> userList = new ArrayList<>();
    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.fkmalls.meidusha.meidusha.auth.MyLog )")
    public void logPoinCut() {
    }

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("logPoinCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MakeSnowId snowId = new MakeSnowId(0,0);
        UserEntity userEntity=userUtil.findUserByTokenUserId(request.getHeader("authorization").split(" ")[1]);
        String doUser=userEntity.getNickName();
        String userUrl=userEntity.getImagePath();
        userList.add(doUser);
        sysLog.setUserUrl(userUrl);
        sysLog.setUserName(doUser);
        sysLog.setId(snowId.nextId());
        sysLog.setUrl(request.getRequestURL().toString());
        sysLog.setHttpMethod(request.getMethod());
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        sysLog.setRequest(getParams(joinPoint));
        sysLog.setCreateDate(dateUtil.getNow());
    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("logPoinCut()")
    public void doAfter() throws Throwable {
        // 接口结束后换行，方便分割查看
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPoinCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        //获取注解的value值
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        MyLog myLog = signature.getMethod().getDeclaredAnnotation(MyLog.class);
        String value = myLog.value();
        String response= JSONObject.toJSONString(result);
        JSONObject jsonObject= JSON.parseObject(response);
        int status= (int) jsonObject.get("code");
        sysLog.setStatus(status);
        sysLog.setResponse(JSONObject.toJSONString(result));
        sysLog.setOperation(value);
        if (value.equals("执行测试用例")){//当"执行测试用例"打印日志时，仅做更新创建时间，除非当天第一次执行则新增
            if (sysLogService.findNewCreateDate(userList.get(0)) == null){
                sysLogService.saveLog(sysLog);
                return result;
            }
            SysLog sysLogStart=sysLogService.findNewCreateDate(userList.get(0));//查询最新创建时间的日志
            userList.clear();
            LocalDateTime newLogDate=sysLogStart.getCreateDate();
            LocalDateTime startDate=dateUtil.getNowStart();//获取当天0点
            if (newLogDate.compareTo( startDate ) > 0){
                sysLogService.updateLog(dateUtil.getNow(),sysLogStart.getId());
            }else {
                sysLogService.saveLog(sysLog);
            }
        }else {
            sysLogService.saveLog(sysLog);
        }
        return result;
    }


    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(LogPrint.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }

    private String getParams(JoinPoint joinPoint) {
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                Object arg = joinPoint.getArgs()[i];
                if ((arg instanceof HttpServletResponse) || (arg instanceof HttpServletRequest)
                        || (arg instanceof MultipartFile) || (arg instanceof MultipartFile[])) {
                    continue;
                }
                try {
                    params += JSONObject.toJSONString(joinPoint.getArgs()[i]);
                } catch (Exception e1) {
                    log.error(e1.getMessage());
                }
            }
        }
        return params;
    }

}