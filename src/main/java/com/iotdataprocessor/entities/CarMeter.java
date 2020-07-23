package com.iotdataprocessor.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iotdataprocessor.model.DeviceType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAR_METER")
@Data
@NoArgsConstructor
public class CarMeter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "device_type", nullable = false, length = 50)
	private DeviceType deviceType;
	
	@Column(name = "reading_value", nullable = false)
	private int readingValue;
	
	@Column(name = "reading_unit", nullable = false, length = 50)
	private String readingUnit;

	public CarMeter(DeviceType deviceType, int readingValue, String readingUnit) {
		super();
		this.deviceType = deviceType;
		this.readingValue = readingValue;
		this.readingUnit = readingUnit;
	}
}
