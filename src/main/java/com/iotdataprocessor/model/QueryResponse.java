package com.iotdataprocessor.model;

import lombok.Data;

@Data
public class QueryResponse {

	private QueryType queryType;
	private DeviceType deviceType;
	private String queryValue;
}
