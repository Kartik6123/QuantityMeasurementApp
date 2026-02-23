package UtilityClasses;

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    // Convert this unit to base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToFeet;
    }

    // Convert base unit (feet) to this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToFeet;
    }

    public double getConversionFactor() {
        return conversionFactorToFeet;
    }
}