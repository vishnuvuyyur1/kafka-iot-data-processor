package com.iotdataprocessor.service;

import com.iotdataprocessor.model.QueryRequest;
import com.iotdataprocessor.model.QueryResponse;

public interface IIOTDeviceDataQueryService {

	public QueryResponse claculateReadings(QueryRequest queryRequest);
}
