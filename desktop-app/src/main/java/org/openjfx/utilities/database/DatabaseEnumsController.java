package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseEnumsController extends DatabaseController {

    public static ObservableList<String> getEnums(String sql) {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try (
                Connection conn = connect();
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
            return null;
        }
    }

    public static ObservableList<String> getEnums(Connection conn, String sql) {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(rs.getString("enumlabel"));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static ObservableList<String> getEmployeePositions() {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'employee_position';";
        return getEnums(sql);
    }

    public static ObservableList<String> getEmployeeCategories() {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'employee_category';";
        return getEnums(sql);
    }

    public static ObservableList<String> addNewEmployeePosition(String position) {
        String sql = "alter type employee_position add value '" + position + "';";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getEmployeePositions();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ObservableList<String> addNewFacilityStatus(String status) {
        String sql = "alter type facility_status add value '" + status + "';";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getFacilityStatuses();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ObservableList<String> getFacilityStatuses() {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'facility_status';";
        return getEnums(sql);
    }

    public static ObservableList<String> getFacilityCategories() {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'facility_category';";
        return getEnums(sql);
    }

    public static ObservableList<String> getContractTypes() {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'contract_type';";
        return getEnums(sql);
    }
}
