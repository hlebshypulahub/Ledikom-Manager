package org.openjfx.ledicom;

public class DateTimeFormatter {
    private static final java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static java.time.format.DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}