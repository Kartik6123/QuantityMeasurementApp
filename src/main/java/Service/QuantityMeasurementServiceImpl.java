package Service;

import Exception.QuantityMeasurementException;
import Model.*;
import Repository.IQuantityMeasurementRepository;
import UtilityClasses.IMeasurable;
import UtilityClasses.Quantity;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(QuantityDTO dto) {
        return IMeasurable.getUnitInstance(
                dto.getUnit().getMeasurementType(),
                dto.getUnit().getUnitName());
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> QuantityModel<U> toModel(QuantityDTO dto) {
        U unit = (U) resolveUnit(dto);
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private QuantityDTO fromQuantity(Quantity<? extends IMeasurable> q) {
        String type = q.getUnit().getMeasurementType();
        String name = q.getUnit().getUnitName();
        QuantityDTO.IMeasurableUnit dtoUnit = resolveDtoUnit(type, name);
        return new QuantityDTO(q.getValue(), dtoUnit);
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
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(e.getMessage());
            repository.save(entity);
            return entity;
        }
    }
}
