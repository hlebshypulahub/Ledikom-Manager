package org.openjfx.utilities.converters;

import org.openjfx.utilities.formatters.DateTimeFormatter;

import java.time.LocalDate;

public class StringToLocalDateConverter {
    public static LocalDate convert(String date) {
        if (date != null) {
            return date.equals("") ? null : LocalDate.parse(date, DateTimeFormatter.getDateTimeFormatter());
        } else {
            return null;
        }
    }
}
