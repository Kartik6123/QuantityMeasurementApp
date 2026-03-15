package com.app.quantitymeasurement.entity;

public class QuantityDTO {

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCH, YARDS, CENTIMETERS;
        public String getUnitName()        { return name(); }
        public String getMeasurementType() { return "LENGTH"; }
    }

    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND;
        public String getUnitName()        { return name(); }
        public String getMeasurementType() { return "WEIGHT"; }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;
        public String getUnitName()        { return name(); }
        public String getMeasurementType() { return "VOLUME"; }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;
        public String getUnitName()        { return name(); }
        public String getMeasurementType() { return "TEMPERATURE"; }
    }

    private double value;
    private IMeasurableUnit unit;

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit  = unit;
    }

    public double getValue()         { return value; }
    public IMeasurableUnit getUnit() { return unit;  }
    public void setValue(double value)        { this.value = value; }
    public void setUnit(IMeasurableUnit unit) { this.unit  = unit;  }

    @Override
    public String toString() {
        return "QuantityDTO(" + value + ", " + unit.getUnitName() + ")";
    }
}
