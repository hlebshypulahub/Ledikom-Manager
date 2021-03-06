package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.ledicom.entities.Task;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.converters.SqlDateStringConverter;
import org.openjfx.utilities.exceptions.EmployeeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseEmployeeController extends DatabaseController {

    private DatabaseEmployeeController() {

    }

    public static boolean addEmployee(Employee employee) throws SQLException {
        String sql = "SELECT EXISTS(SELECT last_name, first_name, patronymic FROM employee WHERE last_name = ? AND first_name = ? AND patronymic = ?);";

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
                    "position, category, category_num, category_assignment_date, maternity_start_date, maternity_end_date, five_year_start, five_year_end, course_deadline_date, children_data, note) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
                ps2.setDate(16, SqlDateStringConverter.stringToSqlDate(employee.getCourseDeadlineDate()));
                ps2.setString(17, employee.getChildrenData());
                ps2.setString(18, employee.getNote());
                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean updateEmployee() throws SQLException {

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
                    "    course_deadline_date = ?, " +
                    "    course_hours_sum = ?, " +
                    "    children_data = ?, " +
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
                ps2.setDate(15, SqlDateStringConverter.stringToSqlDate(Global.getEmployee().getCourseDeadlineDate()));
                ps2.setInt(16, Global.getEmployee().getCourseHoursSum());
                ps2.setString(17, Global.getEmployee().getChildrenData());
                ps2.setString(18, Global.getEmployee().getNote());
                ps2.setInt(19, Global.getEmployee().getId());

                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static ObservableList<Employee> allEmployeeList() {
        String sql = "SELECT * FROM employee_data_view where is_active is not false order by id_employee desc;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> dobNotificationsEmployeeList() {
        String sql = "select * from eployee_dob_with_period_view;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> employeeList(String sql) {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        try (
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

        try (
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
        employee.setChildrenData(rs.getString("children_data"));
        employee.setNote(rs.getString("note"));
        employee.setDobAge(rs.getInt("dob_age"));
        employee.setFullName();
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
        String sql = "select distinct edv.*\n" +
                "from employee_data_view edv\n" +
                "         left join employee_facility ef on ef.id_employee = edv.id_employee\n" +
                "where ((id_facility is not null and id_facility != " + Global.getFacility().getId() + "\n" +
                "   or id_facility is null) and edv.id_employee not in (select id_employee from employee_facility where id_facility = " + Global.getFacility().getId() + "))" +
                "and is_active is not false;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> getEmployeesForFacility() {
        String sql = "select * " +
                "from employee_data_view edv " +
                "         join employee_facility ef on ef.id_employee = edv.id_employee " +
                "where id_facility = " + Global.getFacility().getId() + " and is_active is not false;";
        return employeeList(sql);
    }

    public static ObservableList<Employee> getEmployeesForFacility(int facilityId) {
        String sql = "select * " +
                "from employee_data_view edv " +
                "         join employee_facility ef on ef.id_employee = edv.id_employee " +
                "where id_facility = " + facilityId + " and is_active is not false;";
        return employeeList(sql);
    }

    public static boolean addEmployeeToFacility(EmployeeContract contract) throws SQLException {

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
            return false;
        }

        return true;
    }

    public static ObservableList<EmployeeContract> employeeContractList() {
        String sql = "select type, start_date, expiration_date, f.name, f.id_facility from contract " +
                "join facility f on contract.id_facility = f.id_facility where id_employee = " + Global.getEmployee().getId();

        ObservableList<EmployeeContract> contractList = FXCollections.observableArrayList();

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeContract employeeContract = new EmployeeContract();
                employeeContract.setType(rs.getString("type"));
                employeeContract.setStartDate(SqlDateStringConverter.sqlDateToString(rs.getDate("start_date")) == null ? "Не задано"
                        : SqlDateStringConverter.sqlDateToString(rs.getDate("start_date")));
                employeeContract.setEndDate(SqlDateStringConverter.sqlDateToString(rs.getDate("expiration_date")) == null ? "Не задано"
                        : SqlDateStringConverter.sqlDateToString(rs.getDate("expiration_date")));
                employeeContract.setFacilityName(rs.getString("name"));
                employeeContract.setFacilityId(rs.getInt("id_facility"));
                contractList.add(employeeContract);
            }
            return contractList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static boolean deleteEmployee() {
        String sql = "update employee set is_active = false where id_employee = " + Global.getEmployee().getId() + ";";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public static boolean deleteEmployeeFromFacility() {
        String sql = "delete from employee_facility where id_employee = " + Global.getEmployee().getId() + " and id_facility = " + Global.getFacility().getId() + ";";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public static ArrayList<String> getAllChildrenData() throws SQLException {
        String sql = "SELECT children_data FROM employee_data_view where is_active is not false;";

        ArrayList<String> data = new ArrayList<>();

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("children_data"));
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static ArrayList<Employee> getEmployeePositions() throws SQLException {
        String sql = "select position, COALESCE(category, '') as category from employee_data_view where is_active is not false and position::varchar LIKE ANY (is_pharm());";

        ArrayList<Employee> data = new ArrayList<>();

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setPosition(rs.getString("position"));
                employee.setCategory(rs.getString("category"));
                data.add(employee);
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static void updateContract(EmployeeContract contract) throws SQLException {

        String sql = "update contract set type = '" + contract.getType() + "', "
                + "start_date = '" + SqlDateStringConverter.stringToSqlDate(contract.getStartDate()) + "', "
                + "expiration_date = '" + SqlDateStringConverter.stringToSqlDate(contract.getEndDate()) + "' "
                + " where id_employee = " + Global.getEmployee().getId() + " and id_facility = " + contract.getFacilityId() + ";";

        psExecute(sql);
    }

    public static ObservableList<Task> employeeTasks() throws SQLException {
        String sql = "select * from task order by date;";

        ObservableList<Task> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id_task"));
                task.setEmployee(getEmployee(rs.getInt("id_employee")));
                task.setDate(SqlDateStringConverter.sqlDateToString(rs.getDate("date")));
                task.setTask(rs.getString("description"));
                observableList.add(task);
            }
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static void addTask(Task task) throws SQLException {
        String sql = "insert into task (id_employee, date, description) values (" + task.getEmployee().getId() + "," +
                (SqlDateStringConverter.stringToSqlDate(task.getDate()) == null ? "null,'" : "'" + (SqlDateStringConverter.stringToSqlDate(task.getDate()) + "','"))
                + task.getTask() + "');";

        psExecute(sql);
    }

    public static void deleteTask(int id) throws SQLException {
        String sql = "delete from task where id_task = " + id;

        psExecute(sql);
    }
}
