package Main;

import UtitlityClasses.FeetEquality;
import UtitlityClasses.InchesEquality;

import java.util.Scanner;

public class FeetMain {

    public static void main(String[] args) {

        // Feet check
        System.out.println("Feet Equality: " +
                checkFeetEquality("1.0", "1.0"));

        // Inches check
        System.out.println("Inches Equality: " +
                checkInchesEquality("1.0", "1.0"));
    }

    // Static method for Feet validation + equality
    public static boolean checkFeetEquality(String input1, String input2) {

        if (!isNumeric(input1) || !isNumeric(input2)) {
            System.out.println("Invalid Feet Input");
            return false;
        }

        FeetEquality.Feet f1 =
                new FeetEquality.Feet(Double.parseDouble(input1));

        FeetEquality.Feet f2 =
                new FeetEquality.Feet(Double.parseDouble(input2));

        return f1.equals(f2);
    }

    // Static method for Inches validation + equality
    public static boolean checkInchesEquality(String input1, String input2) {

        if (!isNumeric(input1) || !isNumeric(input2)) {
            System.out.println("Invalid Inches Input");
            return false;
        }

        InchesEquality.Inches i1 =
                new InchesEquality.Inches(Double.parseDouble(input1));

        InchesEquality.Inches i2 =
                new InchesEquality.Inches(Double.parseDouble(input2));

        return i1.equals(i2);
    }

    private static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
