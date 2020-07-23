package com.iotdataprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IotData {
	
	private DeviceType deviceType;
	private int readingValue;
	private String readingUnit;
}
