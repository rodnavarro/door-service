package com.sample.doorservice.service;

import com.sample.doorservice.model.LogType;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LogService {
    public void log(LogType logTypeEnum, String message){
        //TODO : Implement here a way to ship this logs for observability
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.toString() + "\t"+ logTypeEnum.name() + "\t" + message);
    }
}
