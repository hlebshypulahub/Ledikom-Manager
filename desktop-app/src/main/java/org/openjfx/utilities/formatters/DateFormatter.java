package org.openjfx.utilities.formatters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private DateFormatter() {

    }

    public static String format(LocalDate date) {
        return date == null ? "" : date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
