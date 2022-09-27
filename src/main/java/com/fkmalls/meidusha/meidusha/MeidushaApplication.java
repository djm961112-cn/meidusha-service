package com.fkmalls.meidusha.meidusha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MeidushaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeidushaApplication.class, args);
        System.out.println("...start success!");
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
