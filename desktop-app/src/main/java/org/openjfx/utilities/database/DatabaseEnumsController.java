package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseEnumsController extends DatabaseController {

    private DatabaseEnumsController() {

    }

    public static ObservableList<String> getEnums(String sql) throws SQLException {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(rs.getString("enumlabel"));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static ObservableList<String> getEmployeePositions() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'employee_position';";
        return getEnums(sql);
    }

    public static ObservableList<String> getEmployeeCategories() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'employee_category';";
        return getEnums(sql);
    }

    public static ObservableList<String> addNewEmployeePosition(String position) throws SQLException {
        String sql = "alter type employee_position add value '" + position + "';";

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getEmployeePositions();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static ObservableList<String> addNewFacilityStatus(String status) throws SQLException {
        String sql = "alter type facility_status add value '" + status + "';";

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getFacilityStatuses();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static ObservableList<String> getFacilityStatuses() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'facility_status';";
        return getEnums(sql);
    }

    public static ObservableList<String> getFacilityCategories() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'facility_category';";
        return getEnums(sql);
    }

    public static ObservableList<String> getDeviceTypes() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'device_type';";
        return getEnums(sql);
    }

    public static ObservableList<String> getContractTypes() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'contract_type';";
        return getEnums(sql);
    }

    public static ObservableList<String> addNewDeviceType(String text) throws SQLException {
        String sql = "alter type device_type add value '" + text + "';";

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getDeviceTypes();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
