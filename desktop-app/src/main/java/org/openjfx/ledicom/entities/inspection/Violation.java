package org.openjfx.ledicom.entities.inspection;

import org.openjfx.utilities.formatters.DateFormatter;

import java.time.LocalDate;

public class Violation {
    private int id;
    private int idEmployee;
    private String note;
    private String correctionTerm;
    private String correctionDate;

    public Violation(int id, int idEmployee, String note, String correctionTerm, String correctionDate) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.note = note;
        this.correctionTerm = correctionTerm;
        this.correctionDate = correctionDate;
    }

    public Violation(int idEmployee, String note, LocalDate correctionTerm, LocalDate correctionDate) {
        this.idEmployee = idEmployee;
        this.note = note;
        this.correctionTerm = DateFormatter.format(correctionTerm);
        this.correctionDate = DateFormatter.format(correctionDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCorrectionTerm() {
        return correctionTerm;
    }

    public void setCorrectionTerm(String correctionTerm) {
        this.correctionTerm = correctionTerm;
    }

    public String getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(String correctionDate) {
        this.correctionDate = correctionDate;
    }
}
