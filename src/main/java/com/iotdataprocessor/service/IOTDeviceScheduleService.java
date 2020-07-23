package com.iotdataprocessor.service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iotdataprocessor.model.DeviceType;
import com.iotdataprocessor.model.IotData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IOTDeviceScheduleService {
	
	 protected String publishEndpoint = "http://localhost:8080/processor/iotdata";
	
	@Autowired
    private RestTemplate restTemplate;

	 public Runnable publishIotData() {
	        return () -> 
	        {
	        	log.info("Publishing IOT data " + Instant.now().toString());
	        	publishCardata();
	        	publishHeartdata();
	        	publishThermostatdata();
	        };
	    }
	 
	 @Async
	 public void publishCardata() {
		 int readingValue = ThreadLocalRandom.current().nextInt(9, 58 + 1);
		 IotData carMeterData = new IotData(DeviceType.CARFUEL_METER, readingValue, "carunits"); 
		 restTemplate.postForEntity(publishEndpoint, carMeterData, IotData.class);
	 }
	 
	 @Async
	 public void publishHeartdata() {
		 int readingValue = ThreadLocalRandom.current().nextInt(61, 99 + 1);
		 IotData heartMeterData = new IotData(DeviceType.HEART_METER, readingValue, "heartunits");
		 restTemplate.postForEntity(publishEndpoint, heartMeterData, IotData.class);
	 }
	 
	 @Async
	 public void publishThermostatdata() {
		 int readingValue = ThreadLocalRandom.current().nextInt(101, 158 + 1);
		 IotData thermostatMeterData = new IotData(DeviceType.THERMOSTAT_METER, readingValue, "thermostatunits");
		 restTemplate.postForEntity(publishEndpoint, thermostatMeterData, IotData.class);
	 }
}
