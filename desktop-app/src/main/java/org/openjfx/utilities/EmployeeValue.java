package org.openjfx.utilities;

public enum EmployeeValue {
    PHONE("phone", "Телефон"),
    SALARY("salary", "Оклад"),
    PPE("PPE", "Дата выдачи СИЗ"),
    HIRING_DATE("hiringDate", "Дата приёма на работу"),
    DOB("DOB", "Дата рождения"),
    CHILDREN_NUMBER("childrenNumber", "Количество детей"),
    NOTE("note", "Примечание");

    private final String value;
    private final String name;

    EmployeeValue(String value, String name) {
        this.value = value;
        this.name = name;
    }

//    public static Comparator<String> setComparator(ObservableList<Employee> employeeList, EmployeeValue employeeValue) {
//        switch (employeeValue) {
//            case SALARY:
//            case CHILDREN_NUMBER:
//                return new IntComparator();
//            case PPE:
//            case DOB:
//            case HIRING_DATE:
//                return new DateComparator();
//            default:
//                return null;
//        }
//    }

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
