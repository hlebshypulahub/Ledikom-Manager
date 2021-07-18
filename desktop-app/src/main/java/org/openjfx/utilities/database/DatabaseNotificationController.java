package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.utilities.converters.SqlDateStringConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseNotificationController extends DatabaseController {

    private DatabaseNotificationController() {

    }

    public static void dobNotificationsPeriodEdit(int value) throws SQLException {
        String sql = "create or replace function dob_notifications_period()\n" +
                "    returns integer as\n" +
                "$$\n" +
                "begin\n" +
                "    return " + value + ";\n" +
                "end;\n" +
                "$$\n" +
                "    language 'plpgsql';";

        psExecute(sql);
    }

    public static int getNotificationsPeriod(String sql) {
        int value = 60;

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getInt("notifications_period");
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return value;
    }

    public static int getDobNotificationsPeriod() {
        String sql = "select * from dob_notifications_period() as notifications_period;";
        return getNotificationsPeriod(sql);
    }

    public static void setOnApStart(Boolean value) throws SQLException {
        String sql = "create or replace function dob_notifications_on_app_start() returns bool\n" +
                "    language plpgsql\n" +
                "as\n" +
                "$$\n" +
                "BEGIN\n" +
                "    RETURN " + value.toString() + ";\n" +
                "END;\n" +
                "$$;";

        psExecute(sql);
    }

    public static boolean getOnAppStart() {
        String sql = "select dob_notifications_on_app_start() as value;";

        boolean value = true;

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getBoolean("value");
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return value;
    }

    public static void contractNotificationsPeriodEdit(int value) throws SQLException {
        String sql = "create or replace function contract_notifications_period()\n" +
                "    returns integer as\n" +
                "$$\n" +
                "begin\n" +
                "    return " + value + ";\n" +
                "end;\n" +
                "$$\n" +
                "    language 'plpgsql';";

        psExecute(sql);
    }

    public static ObservableList<EmployeeContract> contractNotificationsList() throws SQLException {
        String sql = "select * from full_employee_contract_view";

        ObservableList<EmployeeContract> employeeContractList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeContract employeeContract = new EmployeeContract();

                Employee employee = DatabaseEmployeeController.getEmployee(rs.getInt("id_employee"));
                employeeContract.setEmployee(employee);

                employeeContract.setFacilityName(rs.getString("name"));
                employeeContract.setType(rs.getString("type"));
                employeeContract.setStartDate(SqlDateStringConverter.sqlDateToString(rs.getDate("start_date")));
                employeeContract.setEndDate(SqlDateStringConverter.sqlDateToString(rs.getDate("expiration_date")));

                employeeContractList.add(employeeContract);
            }
            return employeeContractList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static int getContractNotificationsPeriod() {
        String sql = "select * from contract_notifications_period() as notifications_period;";
        return getNotificationsPeriod(sql);
    }

    public static void setContractOnApStart(Boolean value) throws SQLException {
        String sql = "create or replace function contract_notifications_on_app_start() returns bool\n" +
                "    language plpgsql\n" +
                "as\n" +
                "$$\n" +
                "BEGIN\n" +
                "    RETURN " + value.toString() + ";\n" +
                "END;\n" +
                "$$;";

        psExecute(sql);
    }

    public static boolean getContractOnAppStart() {
        String sql = "select contract_notifications_on_app_start() as value;";

        boolean value = true;

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getBoolean("value");
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return value;
    }
}
