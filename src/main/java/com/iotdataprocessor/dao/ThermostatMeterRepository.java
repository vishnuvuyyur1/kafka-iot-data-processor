package com.iotdataprocessor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iotdataprocessor.entities.ThermostatMeter;

public interface ThermostatMeterRepository extends JpaRepository<ThermostatMeter, Long> {

}
