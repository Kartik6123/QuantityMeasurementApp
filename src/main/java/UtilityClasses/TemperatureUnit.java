package UtilityClasses;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            celsius -> celsius,
            celsius -> celsius
    ),

    FAHRENHEIT(
            fahrenheit -> (fahrenheit - 32) * 5.0 / 9.0,
            celsius    -> celsius * 9.0 / 5.0 + 32
    ),

    KELVIN(
            kelvin  -> kelvin - 273.15,
            celsius -> celsius + 273.15
    );

    private final Function<Double, Double> toBaseFn;
    private final Function<Double, Double> fromBaseFn;

    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toBaseFn,
                    Function<Double, Double> fromBaseFn) {
        this.toBaseFn   = toBaseFn;
        this.fromBaseFn = fromBaseFn;
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBaseFn.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromBaseFn.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
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