package org.openjfx.utilities.formatters;

public final class DateTimeFormatter {
    private static final java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DateTimeFormatter() {

    }

    public static java.time.format.DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
