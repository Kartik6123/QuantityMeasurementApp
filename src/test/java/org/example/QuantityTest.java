package org.example;

import UtilityClasses.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPS = 1e-6;

    // ---------------- LENGTH TESTS ----------------

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> f =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> i =
                new Quantity<>(12.0, LengthUnit.INCH);

        assertEquals(f, i);
    }

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> f =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                f.convertTo(LengthUnit.INCH);

        assertEquals(12.0, result.getValue(), EPS);
    }

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> f =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> i =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                f.add(i, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPS);
    }

    // ---------------- WEIGHT TESTS ----------------

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> g =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(kg, g);
    }

    @Test
    void testWeightConversion() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                kg.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> kg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> g =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                kg.add(g, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPS);
    }

    // ---------------- CROSS CATEGORY SAFETY ----------------

    @Test
    void testCrossCategoryComparison() {

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }

    // ---------------- VALIDATION ----------------

    @Test
    void testNullUnitConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testInvalidValueConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }
      // ---------------- EQUALITY TESTS ----------------

    @Test
    void testEquality_LitreToLitre_SameValue() {
        Quantity<VolumeUnit> v1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(v1, v2);
    }

    @Test
    void testEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(litre, ml);
    }

    @Test
    void testEquality_LitreToGallon() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        assertEquals(litre, gallon);
    }

    @Test
    void testEquality_VolumeVsLength() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(volume, length);
    }

    @Test
    void testEquality_VolumeVsWeight() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(volume, weight);
    }

    // ---------------- CONVERSION TESTS ----------------

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> ml =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, ml.getValue(), EPS);
        assertEquals(VolumeUnit.MILLILITRE, ml.getUnit());
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> gallon =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> litre =
                gallon.convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, litre.getValue(), EPS);
    }

    @Test
    void testConversion_RoundTrip() {
        Quantity<VolumeUnit> original =
                new Quantity<>(1.5, VolumeUnit.LITRE);

        Quantity<VolumeUnit> converted =
                original.convertTo(VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(original.getValue(),
                converted.getValue(), EPS);
    }

    // ---------------- ADDITION (IMPLICIT TARGET) ----------------

    @Test
    void testAddition_LitrePlusMillilitre() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                litre.add(ml);

        assertEquals(2.0, result.getValue(), EPS);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    void testAddition_MillilitrePlusLitre() {
        Quantity<VolumeUnit> ml =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                ml.add(litre);

        assertEquals(2000.0, result.getValue(), EPS);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    // ---------------- ADDITION (EXPLICIT TARGET) ----------------

    @Test
    void testAddition_ExplicitTarget_Gallon() {
        Quantity<VolumeUnit> litre =
                new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> litre2 =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                litre.add(litre2, VolumeUnit.GALLON);

        assertEquals(2.0, result.getValue(), EPS);
        assertEquals(VolumeUnit.GALLON, result.getUnit());
    }

    // ---------------- VALIDATION TESTS ----------------

    @Test
    void testConstructor_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructor_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN,
                        VolumeUnit.LITRE));
    }

    @Test
    void testAddition_WithZero() {
        Quantity<VolumeUnit> v1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> zero =
                new Quantity<>(0.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                v1.add(zero);

        assertEquals(5.0, result.getValue(), EPS);
        
    }
    @Test
void testSubtraction_SameUnit_Length() {
    Quantity<LengthUnit> q1 =
            new Quantity<>(10.0, LengthUnit.FEET);
    Quantity<LengthUnit> q2 =
            new Quantity<>(5.0, LengthUnit.FEET);

    Quantity<LengthUnit> result = q1.subtract(q2);

    assertEquals(5.0, result.getValue(), 1e-6);
    assertEquals(LengthUnit.FEET, result.getUnit());
}

@Test
void testSubtraction_CrossUnit_Length() {
    Quantity<LengthUnit> q1 =
            new Quantity<>(10.0, LengthUnit.FEET);
    Quantity<LengthUnit> q2 =
            new Quantity<>(6.0, LengthUnit.INCH);

    Quantity<LengthUnit> result = q1.subtract(q2);

    assertEquals(9.5, result.getValue(), 1e-6);
}

@Test
void testSubtraction_ResultNegative() {
    Quantity<WeightUnit> q1 =
            new Quantity<>(2.0, WeightUnit.KILOGRAM);
    Quantity<WeightUnit> q2 =
            new Quantity<>(5.0, WeightUnit.KILOGRAM);

    Quantity<WeightUnit> result = q1.subtract(q2);

    assertEquals(-3.0, result.getValue(), 1e-6);
}

@Test
void testSubtraction_ExplicitTargetUnit() {
    Quantity<VolumeUnit> q1 =
            new Quantity<>(5.0, VolumeUnit.LITRE);
    Quantity<VolumeUnit> q2 =
            new Quantity<>(2.0, VolumeUnit.LITRE);

    Quantity<VolumeUnit> result =
            q1.subtract(q2, VolumeUnit.MILLILITRE);

    assertEquals(3000.0, result.getValue(), 1e-6);
    assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
}

@Test
void testSubtraction_CrossCategory_ShouldThrow() {
    Quantity<LengthUnit> l =
            new Quantity<>(10.0, LengthUnit.FEET);
    Quantity<WeightUnit> w =
            new Quantity<>(5.0, WeightUnit.KILOGRAM);

    assertThrows(IllegalArgumentException.class,
            () -> l.subtract((Quantity) w));
}

    // ==================== UC14: TEMPERATURE TESTS ==========================

    // ── Equality: same unit ────────────────────────────────────────────────

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(0.0, TemperatureUnit.CELSIUS));
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertEquals(
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_KelvinToKelvin_SameValue() {
        assertEquals(
                new Quantity<>(273.15, TemperatureUnit.KELVIN),
                new Quantity<>(273.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertEquals(
                new Quantity<>(0.0,  TemperatureUnit.CELSIUS),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertEquals(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testTemperatureEquality_CelsiusToKelvin_0Celsius273Kelvin() {
        assertEquals(
                new Quantity<>(0.0,    TemperatureUnit.CELSIUS),
                new Quantity<>(273.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testTemperatureEquality_KelvinToCelsius_100Celsius373Kelvin() {
        assertEquals(
                new Quantity<>(100.0,  TemperatureUnit.CELSIUS),
                new Quantity<>(373.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> c = new Quantity<>(0.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertEquals(c, f);
        assertEquals(f, c);
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertEquals(c, c);
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        assertNotEquals(
                new Quantity<>(50.0,  TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, TemperatureUnit.CELSIUS));
    }

    // ── Conversion ─────────────────────────────────────────────────────────

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_100() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, result.getValue(), EPS);
        assertEquals(TemperatureUnit.FAHRENHEIT, result.getUnit());
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_32() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(0.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_CelsiusToKelvin_0() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.KELVIN);
        assertEquals(273.15, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_KelvinToCelsius_273() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(273.15, TemperatureUnit.KELVIN)
                        .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(0.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_NegativeValue_Celsius_to_Fahrenheit() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(-40.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_SameUnit_ReturnsUnchanged() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, result.getValue(), EPS);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        double original = 37.0;
        double result = new Quantity<>(original, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS)
                .getValue();
        assertEquals(original, result, EPS);
    }

    // ── Unsupported operations ─────────────────────────────────────────────

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> c1.add(c2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> c1.subtract(c2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> c1.divide(c2));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        Quantity<TemperatureUnit> c1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> c2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class, () -> c1.add(c2));
        assertTrue(ex.getMessage().toLowerCase().contains("temperature"));
    }

    // ── Cross-category type safety ─────────────────────────────────────────

    @Test
    void testTemperatureVsLengthIncompatibility() {
        assertNotEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, LengthUnit.FEET));
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        assertNotEquals(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                new Quantity<>(50.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        assertNotEquals(
                new Quantity<>(25.0, TemperatureUnit.CELSIUS),
                new Quantity<>(25.0, VolumeUnit.LITRE));
    }

    // ── Operation support methods ──────────────────────────────────────────

    @Test
    void testOperationSupportMethods_TemperatureNotSupported() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
        assertFalse(TemperatureUnit.KELVIN.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthSupported() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightSupported() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_VolumeSupported() {
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
    }

    // ── Null and edge case validation ──────────────────────────────────────

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, (TemperatureUnit) null));
    }

    @Test
    void testTemperatureNullEqualsReturnsFalse() {
        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertNotEquals(null, c);
    }

    // ── Unit metadata ──────────────────────────────────────────────────────

    @Test
    void testTemperatureUnit_GetUnitName() {
        assertEquals("CELSIUS",    TemperatureUnit.CELSIUS.getUnitName());
        assertEquals("FAHRENHEIT", TemperatureUnit.FAHRENHEIT.getUnitName());
        assertEquals("KELVIN",     TemperatureUnit.KELVIN.getUnitName());
    }

    @Test
    void testTemperatureUnit_GetConversionFactor_ReturnsOne() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionFactor(), EPS);
    }

    // ── validateOperationSupport direct call ───────────────────────────────

    @Test
    void testTemperatureValidateOperationSupport_ThrowsDirectly() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("ADD"));
    }
}
