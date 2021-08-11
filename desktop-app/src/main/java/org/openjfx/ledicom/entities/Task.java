package org.openjfx.ledicom.entities;

public class Task {
    int Id;
    private Employee employee;
    private String task;
    private String date;

    public Task(Employee employee, String date, String task) {
        this.employee = employee;
        this.task = task;
        this.date = date;
    }

    public Task() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmployeeName() {
        return employee.getShortName();
    }

    public Employee getEmployee () {
        return employee;
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
