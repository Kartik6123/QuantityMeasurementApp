package Controller;

import Model.QuantityDTO;
import Model.QuantityMeasurementEntity;
import Service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.compare(q1, q2);
        displayResult(result);
    }

    public void performConversion(QuantityDTO source, QuantityDTO targetUnit) {
        QuantityMeasurementEntity result = service.convert(source, targetUnit);
        displayResult(result);
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.add(q1, q2);
        displayResult(result);
    }

    public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.subtract(q1, q2);
        displayResult(result);
    }

    public void performDivision(QuantityDTO q1, QuantityDTO q2) {
        QuantityMeasurementEntity result = service.divide(q1, q2);
        displayResult(result);
    }

    private void displayResult(QuantityMeasurementEntity entity) {
        if (entity.hasError())
            System.out.println("Error: " + entity.getErrorMessage());
        else
            System.out.println(entity);
    }
}
