package org.openjfx.ledicom.entities;

public class Edu {
    private String name;
    private String graduationDate;

    public Edu(String name, String graduationDate) {
        this.name = name;
        this.graduationDate = graduationDate;
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
