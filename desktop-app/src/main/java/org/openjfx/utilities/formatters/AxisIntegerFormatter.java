package org.openjfx.utilities.formatters;

import javafx.scene.chart.NumberAxis;
import javafx.util.StringConverter;

public class AxisIntegerFormatter {
    private AxisIntegerFormatter() {

    }

    public static void setFormatter(NumberAxis axis) {
        axis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                if (number.intValue() == number.floatValue()) {
                    return String.valueOf(number.intValue());
                }
                return "";
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });
    }
}
