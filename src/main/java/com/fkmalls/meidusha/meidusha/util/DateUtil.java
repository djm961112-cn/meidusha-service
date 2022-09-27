package com.fkmalls.meidusha.meidusha.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * dengjinming
 * 2022/9/14
 */
@Component
public class DateUtil {

    public LocalDateTime getNow(){
        LocalDateTime local = LocalDateTime.now();
        return local;
    }

    public LocalDateTime getNowStart(){
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayStart=time.format(today_start).replace(" ","T");
//        String local=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).replace(" ","T");
//        System.out.println("local:"+local);
        return LocalDateTime.parse(todayStart);
    }
}
