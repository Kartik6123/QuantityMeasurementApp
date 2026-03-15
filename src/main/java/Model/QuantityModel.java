package Model;

import UtilityClasses.IMeasurable;
import UtilityClasses.Quantity;

public class QuantityModel<U extends IMeasurable> {

    private Quantity<U> quantity;
    private U unit;
    private double value;

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
