package UtitlityClasses;

import java.util.Objects;

public final class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityLength(double value, LengthUnit unit) {

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

    public LengthUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // ---------- Conversion ----------

    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite.");

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null.");

        double base = source.convertToBaseUnit(value);
        return target.convertFromBaseUnit(base);
    }

    public QuantityLength convertTo(LengthUnit target) {
        double converted = convert(this.value, this.unit, target);
        return new QuantityLength(converted, target);
    }

    // ---------- Equality ----------

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

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    // ---------- Addition (UC7 Compatible) ----------

    private static double addInBaseUnit(
            QuantityLength q1,
            QuantityLength q2) {

        return q1.toBaseUnit() + q2.toBaseUnit();
    }

    public static QuantityLength add(
            QuantityLength q1,
            QuantityLength q2,
            LengthUnit targetUnit) {

        if (q1 == null || q2 == null)
            throw new IllegalArgumentException("Operands cannot be null.");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null.");

        double sumBase = addInBaseUnit(q1, q2);

        double result =
                targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    public static QuantityLength add(
            QuantityLength q1,
            QuantityLength q2) {

        return add(q1, q2, q1.unit);
    }

    public QuantityLength add(
            QuantityLength other,
            LengthUnit targetUnit) {

        return add(this, other, targetUnit);
    }

    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}