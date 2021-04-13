package org.openjfx.utilities.comparators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateComparator implements Comparator<String> {
    @Override
    public int compare(String date1, String date2) {
        return date1 == null ? -1 : date2 ==  null ? 1 : LocalDate.parse(date1, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        .compareTo(LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
