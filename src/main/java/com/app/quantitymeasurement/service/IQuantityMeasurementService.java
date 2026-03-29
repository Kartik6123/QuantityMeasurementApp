package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {
    QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementDTO convert(QuantityDTO source, QuantityDTO target);
    QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2);
    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByType(String measurementType);
    List<QuantityMeasurementDTO> getErrorHistory();
    long getCountByOperation(String operation);
}
