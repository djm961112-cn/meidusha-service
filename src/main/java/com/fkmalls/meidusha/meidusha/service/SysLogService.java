package com.fkmalls.meidusha.meidusha.service;

import com.fkmalls.meidusha.meidusha.dto.Log.LogDto;
import com.fkmalls.meidusha.meidusha.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * dengjinming
 * 2022/9/13
 */
@Service
public class SysLogService implements LogDto {

    @Autowired
    LogDto logDto;

    @Override
    public int saveLog(SysLog sysLog){
        return logDto.saveLog(sysLog);
    }

    @Override
    public List<SysLog> findAllLog(){
        return logDto.findAllLog();
    }

    @Override
    public int updateLog(LocalDateTime createDate,Long id){
        return logDto.updateLog(createDate,id);
    }

    @Override
    public SysLog findNewCreateDate(String userName){
        return logDto.findNewCreateDate(userName);
    }
}
