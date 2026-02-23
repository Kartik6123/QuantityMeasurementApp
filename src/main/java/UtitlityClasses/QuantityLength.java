package UtitlityClasses;

import java.util.Objects;

public final class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityLength(double value, LengthUnit unit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.toFeet(value);
    }

    // ---------------- UC5 STATIC API ----------------

    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null.");
        }

        double valueInFeet = source.toFeet(value);
        return target.fromFeet(valueInFeet);
    }

    public QuantityLength convertTo(LengthUnit target) {
        double convertedValue = convert(this.value, this.unit, target);
        return new QuantityLength(convertedValue, target);
    }

    // ---------------- equals override ----------------

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityLength other = (QuantityLength) obj;

        return Math.abs(
                this.toBaseUnit() - other.toBaseUnit()
        ) < EPSILON;
    }

    // ---------------- PRIVATE BASE ADDITION ----------------

    private static double addInBaseUnit(
            QuantityLength q1,
            QuantityLength q2) {

        double base1 = q1.toBaseUnit();
        double base2 = q2.toBaseUnit();

        return base1 + base2;
    }

    // ---------------- UC7 ADD WITH TARGET ----------------

    public static QuantityLength add(
            QuantityLength q1,
            QuantityLength q2,
            LengthUnit targetUnit) {

        if (q1 == null || q2 == null) {
            throw new IllegalArgumentException("Operands cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        if (!Double.isFinite(q1.value) || !Double.isFinite(q2.value)) {
            throw new IllegalArgumentException("Values must be finite.");
        }

        double sumInFeet = addInBaseUnit(q1, q2);

        double resultValue = targetUnit.fromFeet(sumInFeet);

        return new QuantityLength(resultValue, targetUnit);
    }

    // ---------------- UC6 BACKWARD COMPATIBILITY ----------------

    public static QuantityLength add(
            QuantityLength q1,
            QuantityLength q2) {

        return add(q1, q2, q1.unit);
    }

    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    public QuantityLength add(
            QuantityLength other,
            LengthUnit targetUnit) {

        return add(this, other, targetUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}