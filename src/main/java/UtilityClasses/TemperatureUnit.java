package UtilityClasses;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    // ── Enum Constants ────────────────────────────────────────────────────
    // Base unit is CELSIUS.
    // Each constant stores:
    //   • toBase   : lambda that converts THIS unit → Celsius
    //   • fromBase : lambda that converts Celsius  → THIS unit

    CELSIUS(
            celsius -> celsius,                        // Celsius → Celsius (identity)
            celsius -> celsius                         // Celsius → Celsius (identity)
    ),

    FAHRENHEIT(
            fahrenheit -> (fahrenheit - 32) * 5.0 / 9.0,   // °F → °C
            celsius    -> celsius * 9.0 / 5.0 + 32          // °C → °F
    ),

    KELVIN(
            kelvin  -> kelvin - 273.15,                // K  → °C
            celsius -> celsius + 273.15                // °C → K
    );

    // ── Fields ────────────────────────────────────────────────────────────

    private final Function<Double, Double> toBaseFn;    // this unit → Celsius
    private final Function<Double, Double> fromBaseFn;  // Celsius   → this unit

    // Lambda: TemperatureUnit does NOT support arithmetic operations
    private final SupportsArithmetic supportsArithmetic = () -> false;

    // ── Constructor ───────────────────────────────────────────────────────

    TemperatureUnit(Function<Double, Double> toBaseFn,
                    Function<Double, Double> fromBaseFn) {
        this.toBaseFn   = toBaseFn;
        this.fromBaseFn = fromBaseFn;
    }

    // ── IMeasurable — Mandatory Methods ───────────────────────────────────

    @Override
    public double getConversionFactor() {
        // Temperature has no single linear factor — return 1.0 as placeholder
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        // Converts this unit's value to Celsius (base)
        return toBaseFn.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        // Converts a Celsius (base) value back to this unit
        return fromBaseFn.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public String getMeasurementType() {
        return "TEMPERATURE";
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();   // always false for temperature
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation +
                " operation. Temperature values represent absolute points " +
                "on a scale; arithmetic on absolute temperatures is " +
                "physically meaningless.");
    }
}
