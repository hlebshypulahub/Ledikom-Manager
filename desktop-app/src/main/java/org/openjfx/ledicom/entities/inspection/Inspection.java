package org.openjfx.ledicom.entities.inspection;

import javafx.collections.ObservableList;
import org.openjfx.utilities.formatters.DateFormatter;

import java.time.LocalDate;

public class Inspection {
    private int id;
    private int idEmployee;
    private int idFacility;
    private String date;
    private String note;
    private ObservableList<Checkup> checkupList;

    public Inspection(ObservableList<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    public Inspection(int id, int idEmployee, int idFacility, String date, String note, ObservableList<Checkup> checkupList) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.idFacility = idFacility;
        this.date = date;
        this.note = note;
        this.checkupList = checkupList;
    }

    public Inspection() {

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

    public int getIdFacility() {
        return idFacility;
    }

    public void setIdFacility(int idFacility) {
        this.idFacility = idFacility;
    }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = DateFormatter.format(date);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ObservableList<Checkup> getCheckupList() {
        return checkupList;
    }

    public void setCheckupList(ObservableList<Checkup> checkupList) {
        this.checkupList = checkupList;
    }
}
