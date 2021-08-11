package org.openjfx.ledicom.entities;

public class Task {
    private Employee employee;
    private String task;
    private String date;

    public String getEmployeeName() {
        return employee.getShortName();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
