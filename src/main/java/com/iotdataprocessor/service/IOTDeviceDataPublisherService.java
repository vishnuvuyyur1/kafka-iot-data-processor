package com.iotdataprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.iotdataprocessor.model.IotData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IOTDeviceDataPublisherService {

	@Autowired
	private KafkaTemplate<String, IotData> kafkaTemplate;

	public void sendData(IotData iotData) {
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String publishedData = ow.writeValueAsString(iotData);
			log.info(String.format("$$ -> Producing message --> %s", publishedData));
		} catch (JsonProcessingException e) {
			log.error("Error while logging producing data");
		}

		this.kafkaTemplate.send(iotData.getDeviceType().toString(), iotData);

	}
}
