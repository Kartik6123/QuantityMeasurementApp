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

        // Length Example
        Quantity<LengthUnit> l1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> l2 =
                new Quantity<>(12.0, LengthUnit.INCH);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCH);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        // Weight Example
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
       // ---------- SUBTRACTION DEMO ----------

Quantity<LengthUnit> l4 =
        new Quantity<>(10.0, LengthUnit.FEET);
Quantity<LengthUnit> l6 =
        new Quantity<>(6.0, LengthUnit.INCH);

System.out.println("Subtract: " + l1.subtract(l2));
System.out.println("Subtract (explicit inches): "
        + l4.subtract(l6, LengthUnit.INCH));

// ---------- DIVISION DEMO ----------

System.out.println("Divide: "
        + l1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

Quantity<WeightUnit> w5 =
        new Quantity<>(10.0, WeightUnit.KILOGRAM);
Quantity<WeightUnit> w8 =
        new Quantity<>(5.0, WeightUnit.KILOGRAM);

System.out.println("Weight division: "
        + w5.divide(w8));
    }
}