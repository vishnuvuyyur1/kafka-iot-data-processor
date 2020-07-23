package com.iotdataprocessor.model;

import lombok.Data;

@Data
public class QueryRequest {

	private QueryType queryType;
	private DeviceType deviceType;
}
