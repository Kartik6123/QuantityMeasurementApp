package UtilityClasses;

import java.util.Objects;

public final class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 1e-4; 

    public QuantityWeight(double value, WeightUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite.");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null.");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }


    public QuantityWeight convertTo(WeightUnit target) {

        if (target == null)
            throw new IllegalArgumentException("Target unit cannot be null.");

        double base = this.toBaseUnit();
        double converted = target.convertFromBaseUnit(base);

        return new QuantityWeight(converted, target);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Math.abs(
                this.toBaseUnit() - other.toBaseUnit()
        ) < EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(this.toBaseUnit() / EPSILON);
        return Long.hashCode(rounded);
    }


    private static double addInBaseUnit(
            QuantityWeight w1,
            QuantityWeight w2) {

        return w1.toBaseUnit() + w2.toBaseUnit();
    }

    public static QuantityWeight add(
            QuantityWeight w1,
            QuantityWeight w2,
            WeightUnit targetUnit) {

        if (w1 == null || w2 == null)
            throw new IllegalArgumentException("Operands cannot be null.");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null.");

        double sumBase = addInBaseUnit(w1, w2);
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    public static QuantityWeight add(
            QuantityWeight w1,
            QuantityWeight w2) {

        return add(w1, w2, w1.unit);
    }

    public QuantityWeight add(QuantityWeight other) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null.");

        return add(this, other, this.unit);
    }

    public QuantityWeight add(
            QuantityWeight other,
            WeightUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null.");

        return add(this, other, targetUnit);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}