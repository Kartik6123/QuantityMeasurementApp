package Service;

import Model.QuantityDTO;
import Model.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {
    QuantityMeasurementEntity compare(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementEntity convert(QuantityDTO source, QuantityDTO targetUnit);
    QuantityMeasurementEntity add(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementEntity subtract(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementEntity divide(QuantityDTO q1, QuantityDTO q2);
}
