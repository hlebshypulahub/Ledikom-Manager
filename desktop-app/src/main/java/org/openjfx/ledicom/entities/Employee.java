package org.openjfx.ledicom.entities;

import org.openjfx.utilities.formatters.DateTimeFormatter;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class Employee {
    private int id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String DOB;
    private String phone;
    private String address;
    private String position;
    private String category;
    private String categoryNum;
    private String categoryAssignmentDate;
    private int salary;
    private String PPE;
    private int courseHoursSum;
    private String courseDeadlineDate;
    private String fiveYearStart;
    private String fiveYearEnd;
    private String hiringDate;
    private String maternityStartDate;
    private String maternityEndDate;
    private int dobAge;
    private int childrenNumber;
    private String note;

    public Employee() {
    }

    public Employee(int id, String lastName, String firstName, String patronymic, String DOB,
                    String phone, String address, int salary,
                    String PPE, String hiringDate,
                    String position, String category, String categoryNum, String categoryAssignmentDate,
                    String maternityStartDate, String maternityEndDate, String fiveYearStart, String fiveYearEnd,
                    Integer childrenNumber, String note) {
        this.id = id;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.DOB = DOB;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.PPE = PPE;
        this.hiringDate = hiringDate;
        this.position = position;
        this.category = category;
        this.categoryNum = categoryNum;
        this.categoryAssignmentDate = categoryAssignmentDate;
        this.maternityStartDate = maternityStartDate;
        this.maternityEndDate = maternityEndDate;
        this.fiveYearStart = fiveYearStart;
        this.fiveYearEnd = fiveYearEnd;
        this.childrenNumber = childrenNumber;
        this.note = note;
        setDobAge();
    }

    public Employee(String lastName, String firstName, String patronymic, String DOB,
                    String phone, String address, int salary,
                    String PPE, String hiringDate,
                    String position, String category, String categoryNum, String categoryAssignmentDate,
                    String maternityStartDate, String maternityEndDate, String fiveYearStart, String fiveYearEnd,
                    Integer childrenNumber, String note) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.DOB = DOB;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.PPE = PPE;
        this.hiringDate = hiringDate;
        this.position = position;
        this.category = category;
        this.categoryNum = categoryNum;
        this.categoryAssignmentDate = categoryAssignmentDate;
        this.maternityStartDate = maternityStartDate;
        this.maternityEndDate = maternityEndDate;
        this.fiveYearStart = fiveYearStart;
        this.fiveYearEnd = fiveYearEnd;
        this.childrenNumber = childrenNumber;
        this.note = note;
        setDobAge();
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(Integer childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }

    public String getShortName() {
        return lastName + " " + firstName.charAt(0) + ". " + patronymic.charAt(0) + ".";
    }

    public void setDobAge() {
        dobAge = getDOB() == null ? 0
                : LocalDate.parse(getDOB(), DateTimeFormatter.getDateTimeFormatter()).getDayOfYear() == LocalDate.now().getDayOfYear()
                ? (int) YEARS.between(LocalDate.parse(getDOB(), DateTimeFormatter.getDateTimeFormatter()), LocalDate.now())
                : (int) YEARS.between(LocalDate.parse(getDOB(), DateTimeFormatter.getDateTimeFormatter()), LocalDate.now()) + 1;
    }

    public int getDobAge() {
        return dobAge;
    }

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDobAge(int dobAge) {
        this.dobAge = dobAge;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCourseDeadlineDate() {
        return courseDeadlineDate;
    }

    public void setCourseDeadlineDate(String courseDeadlineDate) {
        this.courseDeadlineDate = courseDeadlineDate;
    }

    public String getMaternityStartDate() {
        return maternityStartDate;
    }

    public void setMaternityStartDate(String maternityStartDate) {
        this.maternityStartDate = maternityStartDate;
    }

    public String getMaternityEndDate() {
        return maternityEndDate;
    }

    public void setMaternityEndDate(String maternityEndDate) {
        this.maternityEndDate = maternityEndDate;
    }

    public String getFiveYearStart() {
        return fiveYearStart;
    }

    public String getCategoryAssignmentDate() {
        return categoryAssignmentDate;
    }

    public void setCategoryAssignmentDate(String categoryAssignmentDate) {
        this.categoryAssignmentDate = categoryAssignmentDate;
    }

    public void setFiveYearStart(String date) {
        this.fiveYearStart = date;
    }

    public String getFiveYearEnd() {
        return fiveYearEnd;
    }

    public void setFiveYearEnd(String date) {
        this.fiveYearEnd = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPPE() {
        return PPE;
    }

    public void setPPE(String PPE) {
        this.PPE = PPE;
    }

    public int getCourseHoursSum() {
        return courseHoursSum;
    }

    public void setCourseHoursSum(int courseHoursSum) {
        this.courseHoursSum = courseHoursSum;
    }

    public int getId() {
        return id;
    }
}
