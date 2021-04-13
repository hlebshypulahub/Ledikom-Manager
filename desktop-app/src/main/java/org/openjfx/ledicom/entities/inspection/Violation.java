package org.openjfx.ledicom.entities.inspection;

public class Violation {
    private int id;
    private int idEmployee;
    private String description;
    private String actionPlan;
    private String employeeName;
    private String correctionTerm;
    private String correctionDate;

    public Violation(int id, int idEmployee, String employeeName, String description, String actionPlan, String correctionTerm, String correctionDate) {
        this.id = id;
        this.employeeName = employeeName;
        this.idEmployee = idEmployee;
        this.description = description;
        this.actionPlan = actionPlan;
        this.correctionTerm = correctionTerm;
        this.correctionDate = correctionDate;
    }

    public Violation(int idEmployee, String employeeName, String description, String actionPlan, String  correctionTerm, String correctionDate) {
        this.idEmployee = idEmployee;
        this.employeeName = employeeName;
        this.description = description;
        this.actionPlan = actionPlan;
        this.correctionTerm = correctionTerm;
        this.correctionDate = correctionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
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
