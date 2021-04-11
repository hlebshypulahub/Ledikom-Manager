package org.openjfx.ledicom.entities;

import org.openjfx.utilities.formatters.DateFormatter;

import java.time.LocalDate;

public class Edu {
    private String name;
    private String graduationDate;

    public Edu(String name, LocalDate graduationDate) {
        this.name = name;
        this.graduationDate = DateFormatter.format(graduationDate);
    }

    public Edu() {

    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
