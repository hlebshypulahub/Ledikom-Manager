package org.openjfx.ledicom.entities;

import org.openjfx.utilities.formatters.DateFormatter;

import java.time.LocalDate;

public class Course {
    String name;
    String description;
    int hours;
    String startDate;
    String endDate;

    public Course(String name, String description, int hours, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.hours = hours;
        this.startDate = DateFormatter.format(startDate);
        this.endDate = DateFormatter.format(endDate);
    }

    public Course() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
