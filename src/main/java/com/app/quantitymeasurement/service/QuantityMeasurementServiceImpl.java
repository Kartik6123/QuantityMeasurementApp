package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized");
    }

    private IMeasurable resolveUnit(QuantityDTO dto) {
        return IMeasurable.getUnitInstance(dto.getUnit().getMeasurementType(), dto.getUnit().getUnitName());
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> QuantityModel<U> toModel(QuantityDTO dto) {
        U unit = (U) resolveUnit(dto);
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private QuantityDTO fromQuantity(Quantity<? extends IMeasurable> q) {
        return new QuantityDTO(q.getValue(), resolveDtoUnit(q.getUnit().getMeasurementType(), q.getUnit().getUnitName()));
    }

    private QuantityDTO.IMeasurableUnit resolveDtoUnit(String type, String name) {
        switch (type.toUpperCase()) {
            case "LENGTH":      return QuantityDTO.LengthUnit.valueOf(name);
            case "WEIGHT":      return QuantityDTO.WeightUnit.valueOf(name);
            case "VOLUME":      return QuantityDTO.VolumeUnit.valueOf(name);
            case "TEMPERATURE": return QuantityDTO.TemperatureUnit.valueOf(name);
            default: throw new QuantityMeasurementException("Unknown type: " + type);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementEntity compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType()))
                throw new QuantityMeasurementException("Cannot compare different measurement categories");
            QuantityModel model1 = toModel(q1);
            QuantityModel model2 = toModel(q2);
            boolean result = model1.getQuantity().equals(model2.getQuantity());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "COMPARE", result);
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            logger.error("Compare failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementEntity convert(QuantityDTO source, QuantityDTO targetUnitDto) {
        try {
            QuantityModel model = toModel(source);
            IMeasurable targetUnit = resolveUnit(targetUnitDto);
            Quantity converted = model.getQuantity().convertTo(targetUnit);
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(source, targetUnitDto, "CONVERT");
            entity.setResult(fromQuantity(converted));
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            logger.error("Convert failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementEntity add(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType()))
                throw new QuantityMeasurementException("Cannot add different measurement categories");
            QuantityModel model1 = toModel(q1);
            QuantityModel model2 = toModel(q2);
            Quantity result = model1.getQuantity().add(model2.getQuantity());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "ADD");
            entity.setResult(fromQuantity(result));
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            logger.error("Add failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementEntity subtract(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType()))
                throw new QuantityMeasurementException("Cannot subtract different measurement categories");
            QuantityModel model1 = toModel(q1);
            QuantityModel model2 = toModel(q2);
            Quantity result = model1.getQuantity().subtract(model2.getQuantity());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "SUBTRACT");
            entity.setResult(fromQuantity(result));
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            logger.error("Subtract failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementEntity divide(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType()))
                throw new QuantityMeasurementException("Cannot divide different measurement categories");
            QuantityModel model1 = toModel(q1);
            QuantityModel model2 = toModel(q2);
            double result = model1.getQuantity().divide(model2.getQuantity());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "DIVIDE");
            entity.setScalarResult(result);
            repository.save(entity);
            return entity;
        } catch (Exception e) {
            logger.error("Divide failed: {}", e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }
}
