package org.openjfx.ledicom.entities.inspection;

import org.openjfx.ledicom.entities.Employee;

public class Violation {
    private int id;
    private Employee employee;
    private String description;
    private String actionPlan;
    private String correctionTerm;
    private String correctionDate;

    public Violation(int id, Employee employee, String description, String actionPlan, String correctionTerm, String correctionDate) {
        this.id = id;
        this.employee = employee;
        this.description = description;
        this.actionPlan = actionPlan;
        this.correctionTerm = correctionTerm;
        this.correctionDate = correctionDate;
    }

    public Violation(Employee employee, String description, String actionPlan, String  correctionTerm, String correctionDate) {
        this.employee = employee;
        this.description = description;
        this.actionPlan = actionPlan;
        this.correctionTerm = correctionTerm;
        this.correctionDate = correctionDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
