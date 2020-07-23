package com.iotdataprocessor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.iotdataprocessor.dao.CarMeterRepository;
import com.iotdataprocessor.dao.HeartMeterRepository;
import com.iotdataprocessor.dao.ThermostatMeterRepository;
import com.iotdataprocessor.entities.CarMeter;
import com.iotdataprocessor.entities.HeartMeter;
import com.iotdataprocessor.entities.ThermostatMeter;
import com.iotdataprocessor.model.IotData;
import com.iotdataprocessor.model.QueryRequest;
import com.iotdataprocessor.model.QueryResponse;

@Service
public class IOTDeviceDataQueryService implements IIOTDeviceDataQueryService{

	@Autowired
	private CarMeterRepository carMeterRepository;

	@Autowired
	private HeartMeterRepository heartMeterRepository;

	@Autowired
	private ThermostatMeterRepository thermostatMeterRepository;

	public QueryResponse claculateReadings(QueryRequest queryRequest) {
		switch (queryRequest.getDeviceType()) {
		case HEART_METER:
			return calculateHeartMeterReadings(queryRequest);
		case CARFUEL_METER:
			return calculateCarMeterReadings(queryRequest);
		case THERMOSTAT_METER:
			return calculateThermostatMeterReadings(queryRequest);
		default:
			return null;
		}	
	}

	private QueryResponse calculateCarMeterReadings(QueryRequest queryRequest) {
		List<CarMeter> carMeterReadings = getReadings(carMeterRepository);
		List<IotData> iotData =	carMeterReadings.stream()
				.map(meterData -> new IotData(meterData.getDeviceType(),meterData.getReadingValue(), meterData.getReadingUnit()))
			    .collect(Collectors.toList());
		String calculatedReading = getCalculatedReadings(queryRequest, iotData);
		System.out.println("Teststing " + carMeterReadings.get(0).getDeviceType());
		QueryResponse queryResponse = buildQueryResponse(queryRequest, calculatedReading);
		return queryResponse;
	}

	private QueryResponse calculateHeartMeterReadings(QueryRequest queryRequest) {
		List<HeartMeter> heartMeterReadings = getReadings(heartMeterRepository);
		List<IotData> iotData =	heartMeterReadings.stream()
				.map(meterData -> new IotData(meterData.getDeviceType(),meterData.getReadingValue(), meterData.getReadingUnit()))
			    .collect(Collectors.toList());
		String calculatedReading = getCalculatedReadings(queryRequest, iotData);
		QueryResponse queryResponse = buildQueryResponse(queryRequest, calculatedReading);
		return queryResponse;
	}

	private QueryResponse calculateThermostatMeterReadings(QueryRequest queryRequest) {
		List<ThermostatMeter> thermostatMeter = getReadings(thermostatMeterRepository);
		List<IotData> iotData =	thermostatMeter.stream()
				.map(meterData -> new IotData(meterData.getDeviceType(),meterData.getReadingValue(), meterData.getReadingUnit()))
			    .collect(Collectors.toList());
		String calculatedReading = getCalculatedReadings(queryRequest, iotData);
		QueryResponse queryResponse = buildQueryResponse(queryRequest, calculatedReading);
		return queryResponse;
	}

	private <T> String getCalculatedReadings(QueryRequest queryRequest, List<IotData> readings) {
		switch (queryRequest.getQueryType()) {
		case AVERAGE:
			return String.valueOf(readings.stream().mapToInt(data -> data.getReadingValue()).average().getAsDouble());
		case MAX:
			return String.valueOf(readings.stream().mapToInt(data -> data.getReadingValue()).max().getAsInt());
		case MIN:
			return String.valueOf(readings.stream().mapToInt(data -> data.getReadingValue()).min().getAsInt());
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> getReadings(@SuppressWarnings("rawtypes") JpaRepository repository) {
		return repository.findAll();
	}
	
	private QueryResponse buildQueryResponse(QueryRequest queryRequest, String calculatedReading) {
		QueryResponse queryResponse = new QueryResponse();
		queryResponse.setDeviceType(queryRequest.getDeviceType());
		queryResponse.setQueryType(queryRequest.getQueryType());
		queryResponse.setQueryValue(calculatedReading);
		return queryResponse;
	}

}
