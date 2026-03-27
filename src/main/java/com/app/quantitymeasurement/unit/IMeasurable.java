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
        if (measurementType == null) throw new IllegalArgumentException("Measurement type cannot be null");
        if (unitName == null)        throw new IllegalArgumentException("Unit name cannot be null");

        // Normalise: accept "LengthUnit", "LENGTH", "length" etc.
        String normalized = measurementType.toUpperCase().replace("UNIT", "").trim();

        switch (normalized) {
            case "LENGTH":
                try { return LengthUnit.valueOf(unitName.toUpperCase()); }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown LENGTH unit: '" + unitName + "'"); }
            case "WEIGHT":
                try { return WeightUnit.valueOf(unitName.toUpperCase()); }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown WEIGHT unit: '" + unitName + "'"); }
            case "VOLUME":
                try { return VolumeUnit.valueOf(unitName.toUpperCase()); }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown VOLUME unit: '" + unitName + "'"); }
            case "TEMPERATURE":
                try { return TemperatureUnit.valueOf(unitName.toUpperCase()); }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown TEMPERATURE unit: '" + unitName + "'"); }
            default:
                throw new IllegalArgumentException("Unknown measurement type: '" + measurementType + "'");
        }
    }
}
