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
        // Default: no-op — all standard units allow all operations.
        // TemperatureUnit overrides this to throw UnsupportedOperationException.
    }
}