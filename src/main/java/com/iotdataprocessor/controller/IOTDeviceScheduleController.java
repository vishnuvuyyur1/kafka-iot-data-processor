package com.iotdataprocessor.controller;

import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iotdataprocessor.service.IOTDeviceScheduleService;

@RestController
public class IOTDeviceScheduleController {


    public static final long FIXED_RATE = 1000;

    @Autowired
    TaskScheduler taskScheduler;
    
    @Autowired
    private IOTDeviceScheduleService iOTDeviceScheduleService;

    ScheduledFuture<?> scheduledFuture;

    @PostMapping(value = "/start")
    ResponseEntity<String> start() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(iOTDeviceScheduleService.publishIotData(), FIXED_RATE);
        return new ResponseEntity<String>("Started Scheduling",HttpStatus.OK);
    }

    @PostMapping(value = "/stop")
    ResponseEntity<Void> stop() {
        scheduledFuture.cancel(false);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
