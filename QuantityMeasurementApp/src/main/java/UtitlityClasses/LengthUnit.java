package UtitlityClasses;

public enum LengthUnit {

    FEET {
        public double toFeet(double value) {
            return value;
        }
    },

    INCH {
        public double toFeet(double value) {
            return value / 12.0;
        }
    },

    YARDS {
        public double toFeet(double value) {
            return value * 3.0; // 1 yard = 3 feet
        }
    },

    CENTIMETERS {
        public double toFeet(double value) {
            return (value * 0.393701) / 12.0;
            // 1 cm = 0.393701 inches
            // inches to feet divide by 12
        }
    };

    public abstract double toFeet(double value);
}