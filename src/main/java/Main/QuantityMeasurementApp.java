package Main;

import UtilityClasses.*;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable>
    void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {

        System.out.println("Compare " + q1 +
                " and " + q2 +
                " -> Equal: " + q1.equals(q2));
    }

    public static <U extends IMeasurable>
    void demonstrateConversion(Quantity<U> quantity, U target) {

        System.out.println("Convert " + quantity +
                " to " + target +
                " -> " + quantity.convertTo(target));
    }

    public static <U extends IMeasurable>
    void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U target) {

        System.out.println("Add " + q1 +
                " and " + q2 +
                " -> " + q1.add(q2, target));
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> l2 =
                new Quantity<>(12.0, LengthUnit.INCH);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCH);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        Quantity<WeightUnit> w1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        demonstrateEquality(v1, v2);

        Quantity<LengthUnit> l4 =
                new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l6 =
                new Quantity<>(6.0, LengthUnit.INCH);

        System.out.println("Subtract: " + l1.subtract(l2));
        System.out.println("Subtract (explicit inches): "
                + l4.subtract(l6, LengthUnit.INCH));

        System.out.println("Divide: "
                + l1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

        Quantity<WeightUnit> w5 =
                new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w8 =
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        System.out.println("Weight division: " + w5.divide(w8));

        // ── TEMPERATURE DEMONSTRATIONS ─────────────────────────────────────

        System.out.println("\n--- Temperature Equality ---");
        Quantity<TemperatureUnit> c0   = new Quantity<>(0.0,    TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f32  = new Quantity<>(32.0,   TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> c100 = new Quantity<>(100.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f212 = new Quantity<>(212.0,  TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> k0   = new Quantity<>(273.15, TemperatureUnit.KELVIN);

        demonstrateEquality(c0,   f32);
        demonstrateEquality(c100, f212);
        demonstrateEquality(c0,   k0);

        System.out.println("\n--- Temperature Conversion ---");
        demonstrateConversion(c100, TemperatureUnit.FAHRENHEIT);
        demonstrateConversion(f32,  TemperatureUnit.CELSIUS);
        demonstrateConversion(c0,   TemperatureUnit.KELVIN);

        System.out.println("\n--- Unsupported Temperature Arithmetic ---");
        try {
            c100.add(c0);
        } catch (UnsupportedOperationException e) {
            System.out.println("add()      → " + e.getMessage());
        }
        try {
            c100.subtract(c0);
        } catch (UnsupportedOperationException e) {
            System.out.println("subtract() → " + e.getMessage());
        }
        try {
            c100.divide(c0);
        } catch (UnsupportedOperationException e) {
            System.out.println("divide()   → " + e.getMessage());
        }
    }
}