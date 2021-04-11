package org.openjfx.utilities.comparators;

import org.openjfx.ledicom.entities.Course;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course course1, Course course2) {
        return -1 * LocalDate.parse(course1.getStartDate(), java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        .compareTo(LocalDate.parse(course2.getStartDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
