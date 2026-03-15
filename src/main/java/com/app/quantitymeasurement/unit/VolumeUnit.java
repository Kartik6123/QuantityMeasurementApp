package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactorToLitre;

    VolumeUnit(double conversionFactorToLitre) {
        this.conversionFactorToLitre = conversionFactorToLitre;
    }

    @Override public double getConversionFactor()            { return conversionFactorToLitre; }
    @Override public double convertToBaseUnit(double value)  { return value * conversionFactorToLitre; }
    @Override public double convertFromBaseUnit(double base) { return base / conversionFactorToLitre; }
    @Override public String getUnitName()                    { return name(); }
    @Override public String getMeasurementType()             { return "VOLUME"; }
}
