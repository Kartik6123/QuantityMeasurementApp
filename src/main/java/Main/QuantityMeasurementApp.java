package Main;

import UtitlityClasses.LengthUnit;
import UtitlityClasses.QuantityLength;

public class QuantityMeasurementApp {

    public static void demonstrateLengthConversion(
            double value,
            LengthUnit from,
            LengthUnit to) {

        double result =
                QuantityLength.convert(value, from, to);

        System.out.println("Convert " + value + " " + from +
                " to " + to + " -> " +
                String.format("%.4f", result) + " " + to);
    }

    public static void demonstrateLengthEquality(
            QuantityLength q1,
            QuantityLength q2) {

        System.out.println("Compare " + q1 +
                " and " + q2 +
                " -> Equal: " + q1.equals(q2));
    }

    public static void demonstrateLengthAddition(
            QuantityLength q1,
            QuantityLength q2) {

        QuantityLength result = q1.add(q2);

        System.out.println("Add " + q1 +
                " and " + q2 +
                " -> Result: " + result);
    }
    public static void demonstrateLengthAdditionWithTarget(
        QuantityLength q1,
        QuantityLength q2,
        LengthUnit target) {

    QuantityLength result =
            q1.add(q2, target);

    System.out.println("Add " + q1 +
            " and " + q2 +
            " in " + target +
            " -> Result: " + result);
}

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0,
                LengthUnit.FEET,
                LengthUnit.INCH);

        demonstrateLengthConversion(3.0,
                LengthUnit.YARDS,
                LengthUnit.FEET);

        demonstrateLengthConversion(2.54,
                LengthUnit.CENTIMETERS,
                LengthUnit.INCH);

        QuantityLength q1 =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength q2 =
                new QuantityLength(12.0, LengthUnit.INCH);

        demonstrateLengthEquality(q1, q2);

        demonstrateLengthAddition(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH)
        );

        demonstrateLengthAddition(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(3.0, LengthUnit.FEET)
        );

demonstrateLengthAdditionWithTarget(
        new QuantityLength(1.0, LengthUnit.FEET),
        new QuantityLength(12.0, LengthUnit.INCH),
        LengthUnit.YARDS
);
    }
}