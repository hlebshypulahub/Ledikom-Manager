package org.openjfx.ledicom.entities.inspection;

import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;

public class Inspection {
    private int id;
    private Employee employee;
    private Facility facility;
    private String date;
    private String note;
    private ObservableList<Checkup> checkupList;

    public Inspection(ObservableList<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    public Inspection(int id, Employee employee, Facility facility, String date, String note, ObservableList<Checkup> checkupList) {
        this.id = id;
        this.employee = employee;
        this.facility = facility;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
