package com.iotdataprocessor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotdataprocessor.entities.CarMeter;

public interface CarMeterRepository extends JpaRepository<CarMeter, Long> {

}
