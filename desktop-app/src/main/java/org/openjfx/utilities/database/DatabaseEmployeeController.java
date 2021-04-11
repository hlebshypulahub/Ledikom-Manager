package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.utilities.exceptions.EmployeeException;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.converters.SqlDateStringConverter;

import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseEmployeeController extends DatabaseController {

    public static boolean addEmployee(Employee employee) throws SQLException {
        String sql = "SELECT EXISTS(SELECT last_name, first_name, patronymic FROM employee WHERE last_name = ? AND first_name = ? AND patronymic = ?);";

        Connection conn = connect();

        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            ps1.setString(1, employee.getLastName());
            ps1.setString(2, employee.getFirstName());
            ps1.setString(3, employee.getPatronymic());
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    MyAlert.showAndWait("ERROR", "Ошибка", "Сотрудник " + employee.getFullName() + " уже есть в базе!", "");
                    throw new EmployeeException("Employee already exists");
                }
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            conn.close();
            return false;
        }

        sql = "insert into employee (last_name, first_name, patronymic) " +
                "values (?, ?, ?) returning id_employee;";

        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            int employeeId = 0;

            ps1.setString(1, employee.getLastName());
            ps1.setString(2, employee.getFirstName());
            ps1.setString(3, employee.getPatronymic());

            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                employeeId = rs.getInt("id_employee");
            }
            rs.close();

            sql = "insert into employee_info (id_employee, dob, phone, address, salary, ppe, hiring_date, " +
                    "position, category, category_num, category_assignment_date, maternity_start_date, maternity_end_date, five_year_start, five_year_end, children_number, note) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                ps2.setInt(1, employeeId);
                ps2.setDate(2, SqlDateStringConverter.stringToSqlDate(employee.getDOB()));
                ps2.setString(3, employee.getPhone());
                ps2.setString(4, employee.getAddress());
                ps2.setInt(5, employee.getSalary());
                ps2.setDate(6, SqlDateStringConverter.stringToSqlDate(employee.getPPE()));
                ps2.setDate(7, SqlDateStringConverter.stringToSqlDate(employee.getHiringDate()));
                ps2.setObject(8, employee.getPosition(), Types.OTHER);
                ps2.setObject(9, employee.getCategory(), Types.OTHER);
                ps2.setString(10, employee.getCategoryNum());
                ps2.setDate(11, SqlDateStringConverter.stringToSqlDate(employee.getCategoryAssignmentDate()));
                ps2.setDate(12, SqlDateStringConverter.stringToSqlDate(employee.getMaternityStartDate()));
                ps2.setDate(13, SqlDateStringConverter.stringToSqlDate(employee.getMaternityEndDate()));
                ps2.setDate(14, SqlDateStringConverter.stringToSqlDate(employee.getFiveYearStart()));
                ps2.setDate(15, SqlDateStringConverter.stringToSqlDate(employee.getFiveYearEnd()));
                ps2.setInt(16, employee.getChildrenNumber());
                ps2.setString(17, employee.getNote());
                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            conn.close();
            return false;
        }

        conn.close();
        return true;
    }

    public static boolean updateEmployee() throws SQLException {
        Connection conn = connect();

        String sql = "update employee set first_name = '" + Global.getEmployee().getFirstName() + "',"
                + "last_name = '" + Global.getEmployee().getLastName() + "', "
                + "patronymic = '" + Global.getEmployee().getPatronymic()
                + "' where id_employee = " + Global.getEmployee().getId() + ";";


        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            ps1.execute();

            sql = "update employee_info " +
                    "set dob = ?, " +
                    "    phone = ?, " +
                    "    address = ?, " +
                    "    salary = ?, " +
                    "    ppe = ?, " +
                    "    hiring_date = ?, " +
                    "    position = ?, " +
                    "    category = ?, " +
                    "    category_num = ?, " +
                    "    category_assignment_date = ?, " +
                    "    maternity_start_date = ?, " +
                    "    maternity_end_date = ?, " +
                    "    five_year_start = ?, " +
                    "    five_year_end = ?, " +
                    "    children_number = ?, " +
                    "    note = ? " +
                    "where id_employee = ?;";

            try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                ps2.setDate(1, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getDOB()));
                ps2.setString(2, Global.getEmployee().getPhone());
                ps2.setString(3, Global.getEmployee().getAddress());
                ps2.setInt(4, Global.getEmployee().getSalary());
                ps2.setDate(5, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getPPE()));
                ps2.setDate(6, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getHiringDate()));
                ps2.setObject(7, Global.getEmployee().getPosition(), Types.OTHER);
                ps2.setObject(8, Global.getEmployee().getCategory(), Types.OTHER);
                ps2.setString(9, Global.getEmployee().getCategoryNum());
                ps2.setDate(10, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getCategoryAssignmentDate()));
                ps2.setDate(11, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getMaternityStartDate()));
                ps2.setDate(12, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getMaternityEndDate()));
                ps2.setDate(13, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getFiveYearStart()));
                ps2.setDate(14, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getFiveYearEnd()));
                ps2.setInt(15, Global.getEmployee().getId());
                ps2.setInt(16, Global.getEmployee().getChildrenNumber());
                ps2.setString(17, Global.getEmployee().getNote());
                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            conn.close();
            return false;
        }

        conn.close();
        return true;
    }

    public static ObservableList<Employee> allEmployeeList() {
        String sql = "SELECT * FROM employee_data_view order by id_employee desc;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> dobNotificationsEmployeeList() {
        String sql = "select * from employee_data_view " +
                "where date_part('doy', dob) - date_part('doy', current_date) between 0 and dob_notifications_period();";
        return employeeList(sql);
    }

    public static ObservableList<Employee> employeeList(String sql) {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                setEmployee(employee, rs);
                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static Employee getEmployee(int employeeId) {
        String sql = "SELECT * FROM employee_data_view WHERE id_employee = " + employeeId + ";";

        Employee employee = new Employee();

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                setEmployee(employee, rs);
            }
            return employee;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void setEmployee(Employee employee, ResultSet rs) throws SQLException {
        employee.setId(rs.getInt("id_employee"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setPatronymic(rs.getString("patronymic"));
        employee.setAddress(rs.getString("address"));
        employee.setPhone(rs.getString("phone"));
        employee.setCourseHoursSum(rs.getInt("course_hours_sum"));
        employee.setDOB(SqlDateStringConverter.sqlDateToString(rs.getDate("DOB")));
        employee.setPPE(SqlDateStringConverter.sqlDateToString(rs.getDate("PPE")));
        employee.setSalary(rs.getInt("salary"));
        employee.setCourseDeadlineDate(SqlDateStringConverter.sqlDateToString(rs.getDate("course_deadline_date")));
        employee.setFiveYearStart(SqlDateStringConverter.sqlDateToString(rs.getDate("five_year_start")));
        employee.setFiveYearEnd(SqlDateStringConverter.sqlDateToString(rs.getDate("five_year_end")));
        employee.setHiringDate(SqlDateStringConverter.sqlDateToString(rs.getDate("hiring_date")));
        employee.setPosition(rs.getString("position"));
        employee.setCategory(rs.getString("category"));
        employee.setCategoryNum(rs.getString("category_num"));
        employee.setCategoryAssignmentDate(SqlDateStringConverter.sqlDateToString(rs.getDate("category_assignment_date")));
        employee.setMaternityStartDate(SqlDateStringConverter.sqlDateToString(rs.getDate("maternity_start_date")));
        employee.setMaternityEndDate(SqlDateStringConverter.sqlDateToString(rs.getDate("maternity_end_date")));
        employee.setChildrenNumber(rs.getInt("children_number"));
        employee.setNote(rs.getString("note"));
        employee.setDobAge();
    }

    public static void addEdu(Edu edu) throws SQLException {
        String sql = "insert into edu (id_employee, name, graduation_date) values (" +
                +Global.getEmployee().getId() + ",'" + edu.getName() + "','" + SqlDateStringConverter.stringToSqlDate(edu.getGraduationDate()) + "') " +
                "on conflict (id_employee) " +
                "do update set name = excluded.name, graduation_date = excluded.graduation_date;";
        psExecute(sql);
    }

    public static Edu getEmployeeEdu() {
        String sql = "select * from edu where id_employee = " + Global.getEmployee().getId() + ";";

        Edu edu = new Edu();

        try (
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                edu.setName(rs.getString("name"));
                edu.setGraduationDate(SqlDateStringConverter.sqlDateToString(rs.getDate("graduation_date")));
            }
            rs.close();
            return edu;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void updateEmployeeDatesOnAppStart() throws SQLException {
        String sql = "select update_employee_dates_on_app_start();";
        psExecute(sql);
    }

    public static ObservableList<Employee> employeeForNotFacility() {
        String sql = "select * " +
                "from employee_data_view edv " +
                "         left join employee_facility ef on ef.id_employee = edv.id_employee " +
                "where id_facility is not null and id_facility != " + Global.getFacility().getId() + " or id_facility is null;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> employeeForFacility() {
        String sql = "select * " +
                "from employee_data_view edv " +
                "         join employee_facility ef on ef.id_employee = edv.id_employee " +
                "where id_facility = " + Global.getFacility().getId() + ";";
        return employeeList(sql);
    }

    public static boolean addEmployeeToFacility(EmployeeContract contract) throws SQLException {
        Connection conn = connect();

        String sql = "SELECT EXISTS(SELECT id_employee, id_facility FROM employee_facility WHERE id_employee = ? AND id_facility = ?);";

        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            ps1.setInt(1, Global.getEmployee().getId());
            ps1.setInt(2, Global.getFacility().getId());
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    MyAlert.showAndWait("ERROR", "Ошибка", "Сотрудник " + Global.getEmployee() + " уже есть на этом объекте!", "");
                    throw new EmployeeException("Employee already exists");
                }
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            conn.close();
            return false;
        }

        sql = "insert into employee_facility (id_employee, id_facility) values ("
                + Global.getEmployee().getId() + ", " + Global.getFacility().getId() + ");";


        try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
            ps2.execute();

            sql = "insert into contract (id_employee, id_facility, type, start_date, expiration_date) values (?, ?, ?, ?, ?);";

            try (PreparedStatement ps3 = conn.prepareStatement(sql)) {
                ps3.setInt(1, Global.getEmployee().getId());
                ps3.setInt(2, Global.getFacility().getId());
                ps3.setObject(3, contract.getType(), Types.OTHER);
                ps3.setDate(4, SqlDateStringConverter.stringToSqlDate(contract.getStartDate()));
                ps3.setDate(5, SqlDateStringConverter.stringToSqlDate(contract.getEndDate()));
                ps3.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            conn.close();
            return false;
        }

        conn.close();
        return true;
    }
}
