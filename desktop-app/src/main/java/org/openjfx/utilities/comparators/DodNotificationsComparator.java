package org.openjfx.utilities.comparators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DodNotificationsComparator implements Comparator<String> {
    @Override
    public int compare(String date1, String date2) {
        return Integer.compare(LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd.MM.yyyy")).getDayOfYear(),
                LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd.MM.yyyy")).getDayOfYear());
    }
}
