package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementCacheRepository.class);
    private static QuantityMeasurementCacheRepository instance;
    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {
        logger.info("QuantityMeasurementCacheRepository initialized");
    }

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null)
            instance = new QuantityMeasurementCacheRepository();
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(cache);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operationType) {
        return cache.stream()
                .filter(e -> !e.hasError() && operationType.equalsIgnoreCase(e.getOperationType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return cache.stream()
                .filter(e -> !e.hasError() && e.getOperand1() != null &&
                        measurementType.equalsIgnoreCase(e.getOperand1().getUnit().getMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalCount() {
        return cache.size();
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }
}
