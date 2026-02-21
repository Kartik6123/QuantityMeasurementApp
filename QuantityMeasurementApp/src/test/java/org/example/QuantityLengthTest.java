package org.example;

import UtitlityClasses.LengthUnit;
import UtitlityClasses.QuantityLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityLengthTest {

    // ----------- YARD TESTS -----------

    @Test
    void testEquality_YardToYard_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(q1, q2);
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.YARDS);

        assertNotEquals(q1, q2);
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);

        assertEquals(yard, feet);
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(feet, yard);
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCH);

        assertEquals(yard, inches);
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(2.0, LengthUnit.FEET);

        assertNotEquals(yard, feet);
    }

    // ----------- CENTIMETER TESTS -----------

    @Test
    void testEquality_CentimeterToCentimeter_SameValue() {
        QuantityLength cm1 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength cm2 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);

        assertEquals(cm1, cm2);
    }

    @Test
    void testEquality_CentimeterToInches_EquivalentValue() {
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(0.393701, LengthUnit.INCH);

        assertEquals(cm, inch);
    }

    @Test
    void testEquality_CentimeterToFeet_NonEquivalentValue() {
        QuantityLength cm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);

        assertNotEquals(cm, feet);
    }

    // ----------- MULTI UNIT TEST -----------

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches);
    }

    @Test
    void testEquality_AllUnits_ComplexScenario() {
        QuantityLength yard = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(72.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches);
    }

    // ----------- OBJECT CONTRACT TESTS -----------

    @Test
    void testEquality_SameReference() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);

        assertEquals(yard, yard);
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);

        assertNotEquals(yard, null);
    }

    @Test
    void testEquality_NullUnit_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new QuantityLength(1.0, null)
        );
    }
}