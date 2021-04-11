package org.openjfx.ledicom.entities;

public class EmployeeCourseData {
    int employeeId;
    String employeeName;
    String position;
    String edu;
    String category;
    String courseName;
    String courseData;
    String nextCourseData;

    public String getNextCourseData() {
        return nextCourseData;
    }

    public void setNextCourseData(String nextCourseData) {
        this.nextCourseData = nextCourseData;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseData() {
        return courseData;
    }

    public void setCourseData(String courseData) {
        this.courseData = courseData;
    }
}
