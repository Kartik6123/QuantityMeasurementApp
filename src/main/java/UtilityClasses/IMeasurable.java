package UtilityClasses;

public interface IMeasurable {

    // ── Mandatory conversion methods ──────────────────────────────────────

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    // ── Functional Interface ───────────────────────────────────────────────

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    // Default lambda: all units support arithmetic unless overridden
    SupportsArithmetic supportsArithmetic = () -> true;

    // ── Default Methods (optional — override only when needed) ─────────────

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }

    String getMeasurementType();

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
