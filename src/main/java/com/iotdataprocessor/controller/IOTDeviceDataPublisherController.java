package com.iotdataprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iotdataprocessor.model.IotData;
import com.iotdataprocessor.service.IOTDeviceDataPublisherService;

@RestController
public class IOTDeviceDataPublisherController {

	@Autowired
	private IOTDeviceDataPublisherService iOTDevicePublisherService;

	@PostMapping(value = "/iotdata")
	public ResponseEntity<IotData> sendMessageToKafkaTopic( @RequestBody IotData iotData) {
		iOTDevicePublisherService.sendData(iotData);
		return new ResponseEntity<IotData>(iotData,HttpStatus.OK);
	}
}
