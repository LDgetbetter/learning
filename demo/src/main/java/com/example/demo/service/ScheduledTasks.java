package com.example.demo.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 5000)    //每5s执行一次，还可以配置其他模式
    public void reportCurrentTime(){
        System.out.println("现在时间： "+ dateFormat.format(new Date()));
    }
}
