package com.iotdataprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.iotdataprocessor.dao.CarMeterRepository;
import com.iotdataprocessor.dao.HeartMeterRepository;
import com.iotdataprocessor.dao.ThermostatMeterRepository;
import com.iotdataprocessor.entities.CarMeter;
import com.iotdataprocessor.entities.HeartMeter;
import com.iotdataprocessor.entities.ThermostatMeter;
import com.iotdataprocessor.model.IotData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IOTDeviceDataConsumerService {
	
	@Autowired
	private CarMeterRepository carMeterRepository;
	
	@Autowired
	private HeartMeterRepository heartMeterRepository;
	
	@Autowired
	private ThermostatMeterRepository thermostatMeterRepository;
	
	
	@KafkaListener(topics = "THERMOSTAT_METER", groupId = "iot-devices")
	public void consumeThermostatData(@Payload IotData iotData){
		logConsumedData(iotData);
		thermostatMeterRepository.save(new ThermostatMeter(iotData.getDeviceType(),iotData.getReadingValue(),iotData.getReadingUnit()));
	}
	
	@KafkaListener(topics = "HEART_METER", groupId = "iot-devices")
	public void consumeHeartRateData(@Payload IotData iotData){
		logConsumedData(iotData);
		heartMeterRepository.save(new HeartMeter(iotData.getDeviceType(),iotData.getReadingValue(),iotData.getReadingUnit()));
	}
	
	@KafkaListener(topics = "CARFUEL_METER", groupId = "iot-devices")
	public void consumeCarfuelData(@Payload IotData iotData){
		logConsumedData(iotData);
		carMeterRepository.save(new CarMeter(iotData.getDeviceType(),iotData.getReadingValue(),iotData.getReadingUnit()));
	}
	
	private void logConsumedData(IotData iotData){
		try {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String	consumedData = ow.writeValueAsString(iotData);
		log.info(String.format("$$ -> Consumed message --> %s", consumedData));
		} catch (JsonProcessingException e) {
			log.error("Error while logging consumed data");
		}
	}
}
