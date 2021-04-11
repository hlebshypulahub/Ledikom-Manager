package org.openjfx.utilities.converters;

public class StringToIntegerConverter {
    public static int convert(String value) {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException nfe) {
            return 0;
        }
    }
}
