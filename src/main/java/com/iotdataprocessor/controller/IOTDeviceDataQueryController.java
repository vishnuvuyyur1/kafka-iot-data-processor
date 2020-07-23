package com.iotdataprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iotdataprocessor.model.QueryRequest;
import com.iotdataprocessor.model.QueryResponse;
import com.iotdataprocessor.service.IIOTDeviceDataQueryService;

@RestController
public class IOTDeviceDataQueryController {

	@Autowired
	private IIOTDeviceDataQueryService iOTDeviceDataQueryService;
	
	@GetMapping(value = "/iotdata")
	public QueryResponse sendMessageToKafkaTopic(@RequestBody QueryRequest queryRequest) {
		return iOTDeviceDataQueryService.claculateReadings(queryRequest);
	}
}
