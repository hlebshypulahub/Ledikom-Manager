package org.openjfx.utilities;

public enum EmployeeTableValue {
    PHONE("phone", "Телефон"),
    SALARY("salary", "Оклад"),
    PPE("PPE", "Дата выдачи СИЗ"),
    HIRING_DATE("hiringDate", "Дата приёма на работу"),
    DOB("DOB", "Дата рождения"),
    CHILDREN_DATA("childrenData", "Дети"),
    NOTE("note", "Примечание");

    private final String value;
    private final String name;

    EmployeeTableValue(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
