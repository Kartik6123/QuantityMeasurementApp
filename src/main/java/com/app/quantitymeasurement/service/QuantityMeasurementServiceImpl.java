package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    @Autowired
    private QuantityMeasurementRepository repository;

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> Quantity<U> toQuantity(QuantityDTO dto) {
        U unit = (U) IMeasurable.getUnitInstance(dto.getMeasurementType(), dto.getUnit());
        return new Quantity<>(dto.getValue(), unit);
    }

    private QuantityMeasurementDTO saveAndReturn(QuantityMeasurementDTO dto) {
        repository.save(dto.toEntity());
        return dto;
    }

    private QuantityMeasurementDTO buildBase(QuantityDTO q1, QuantityDTO q2, String operation) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setThisValue(q1.getValue());
        dto.setThisUnit(q1.getUnit());
        dto.setThisMeasurementType(q1.getMeasurementType());
        dto.setThatValue(q2.getValue());
        dto.setThatUnit(q2.getUnit());
        dto.setThatMeasurementType(q2.getMeasurementType());
        dto.setOperation(operation);
        return dto;
    }

    private QuantityMeasurementDTO buildError(QuantityDTO q1, QuantityDTO q2, String operation, String message) {
        QuantityMeasurementDTO dto = buildBase(q1, q2, operation);
        dto.setError(true);
        dto.setErrorMessage(message);
        return dto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot compare different measurement categories");
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            boolean result = qty1.equals(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "compare");
            dto.setResultString(String.valueOf(result));
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Compare failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "compare", e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO convert(QuantityDTO source, QuantityDTO target) {
        try {
            if (!source.getMeasurementType().equals(target.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot convert between different measurement categories: "
                        + source.getMeasurementType() + " and " + target.getMeasurementType());
            Quantity qty = toQuantity(source);
            IMeasurable targetUnit = IMeasurable.getUnitInstance(target.getMeasurementType(), target.getUnit());
            Quantity converted = qty.convertTo(targetUnit);
            QuantityMeasurementDTO dto = buildBase(source, target, "convert");
            dto.setResultValue(converted.getValue());
            dto.setResultUnit(converted.getUnit().getUnitName());
            dto.setResultMeasurementType(converted.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Convert failed: {}", e.getMessage());
            return saveAndReturn(buildError(source, target, "convert", e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot perform arithmetic between different measurement categories: " + q1.getMeasurementType() + " and " + q2.getMeasurementType());
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            Quantity result = qty1.add(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "add");
            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().getUnitName());
            dto.setResultMeasurementType(result.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Add failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "add", "add Error: " + e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot perform arithmetic between different measurement categories: " + q1.getMeasurementType() + " and " + q2.getMeasurementType());
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            Quantity result = qty1.subtract(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "subtract");
            dto.setResultValue(result.getValue());
            dto.setResultUnit(result.getUnit().getUnitName());
            dto.setResultMeasurementType(result.getUnit().getMeasurementType());
            return saveAndReturn(dto);
        } catch (Exception e) {
            logger.error("Subtract failed: {}", e.getMessage());
            return saveAndReturn(buildError(q1, q2, "subtract", e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getMeasurementType().equals(q2.getMeasurementType()))
                throw new QuantityMeasurementException("Cannot perform arithmetic between different measurement categories");
            Quantity qty1 = toQuantity(q1);
            Quantity qty2 = toQuantity(q2);
            double result = qty1.divide(qty2);
            QuantityMeasurementDTO dto = buildBase(q1, q2, "divide");
            dto.setResultValue(result);
            return saveAndReturn(dto);
        } catch (QuantityMeasurementException e) {
            logger.error("Divide failed: {}", e.getMessage());
            saveAndReturn(buildError(q1, q2, "divide", e.getMessage()));
            throw e;
        } catch (UnsupportedOperationException e) {
            logger.error("Divide failed (unsupported): {}", e.getMessage());
            saveAndReturn(buildError(q1, q2, "divide", e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Divide failed: {}", e.getMessage());
            saveAndReturn(buildError(q1, q2, "divide", e.getMessage()));
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operation));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByThisMeasurementType(measurementType));
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByIsErrorTrue());
    }

    @Override
    public long getCountByOperation(String operation) {
        return repository.countByOperation(operation);
    }
}
