package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;

public class QuantityModel<U extends IMeasurable> {

    private final Quantity<U> quantity;
    private final U unit;
    private final double value;

    public QuantityModel(double value, U unit) {
        this.value    = value;
        this.unit     = unit;
        this.quantity = new Quantity<>(value, unit);
    }

    public Quantity<U> getQuantity() { return quantity; }
    public U getUnit()               { return unit;     }
    public double getValue()         { return value;    }

    @Override
    public String toString() {
        return "QuantityModel(" + value + ", " + unit.getUnitName() + ")";
    }
}
