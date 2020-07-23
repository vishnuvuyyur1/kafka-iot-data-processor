package com.iotdataprocessor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotdataprocessor.entities.HeartMeter;

public interface HeartMeterRepository extends JpaRepository<HeartMeter, Long> {

}
