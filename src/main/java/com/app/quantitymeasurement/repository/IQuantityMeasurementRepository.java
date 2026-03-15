package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operationType);
    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
    int getTotalCount();
    void deleteAll();

    default String getPoolStatistics() { return "No pool statistics available"; }
    default void releaseResources()    {}
}
