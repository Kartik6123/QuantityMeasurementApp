package com.app.quantitymeasurement.unit;

public interface IMeasurable {

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    String getMeasurementType();

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }

    static IMeasurable getUnitInstance(String measurementType, String unitName) {
        switch (measurementType.toUpperCase()) {
            case "LENGTH":      return LengthUnit.valueOf(unitName.toUpperCase());
            case "WEIGHT":      return WeightUnit.valueOf(unitName.toUpperCase());
            case "VOLUME":      return VolumeUnit.valueOf(unitName.toUpperCase());
            case "TEMPERATURE": return TemperatureUnit.valueOf(unitName.toUpperCase());
            default: throw new IllegalArgumentException("Unknown measurement type: " + measurementType);
        }
    }
}
