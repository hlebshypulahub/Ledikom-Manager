package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.exceptions.FacilityException;

import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseFacilityController extends DatabaseController {

    private DatabaseFacilityController() {

    }

    public static boolean addFacility(Facility facility) throws SQLException {
        String sql = "SELECT EXISTS(SELECT name FROM facility WHERE name = ?);";

        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            ps1.setString(1, facility.getName());
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    MyAlert.showAndWait("ERROR", "Ошибка", "Объект " + facility.getName() + " уже есть в базе!", "");
                    throw new FacilityException("Facility already exists");
                }
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return false;
        }

        sql = "insert into facility (name) " +
                "values (?) returning id_facility;";

        try (PreparedStatement ps1 = conn.prepareStatement(sql)) {
            int facilityId = 0;

            ps1.setString(1, facility.getName());

            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                facilityId = rs.getInt("id_facility");
            }
            rs.close();

            sql = "insert into facility_info (id_facility, status, category, schedule, city, address, phone, email) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                ps2.setInt(1, facilityId);
                ps2.setObject(2, facility.getStatus(), Types.OTHER);
                ps2.setObject(3, facility.getCategory(), Types.OTHER);
                ps2.setString(4, facility.getSchedule());
                ps2.setString(5, facility.getCity());
                ps2.setString(6, facility.getAddress());
                ps2.setString(7, facility.getPhone());
                ps2.setString(8, facility.getEmail());
                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean updateFacility() throws SQLException {

        String sql = "update facility " +
                "set name = '" + Global.getFacility().getName() + "'" +
                "where id_facility = " + Global.getFacility().getId() + ";";

        try(PreparedStatement ps1 = conn.prepareStatement(sql)) {
            ps1.execute();

            sql = "update facility_info " +
                    "set status = ?, " +
                    "    category = ?, " +
                    "    schedule = ?, " +
                    "    city = ?, " +
                    "    address = ?, " +
                    "    phone = ?, " +
                    "    email = ? " +
                    "where id_facility = ?;";

            try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                ps2.setObject(1, Global.getFacility().getStatus(), Types.OTHER);
                ps2.setObject(2, Global.getFacility().getCategory(), Types.OTHER);
                ps2.setString(3, Global.getFacility().getSchedule());
                ps2.setString(4, Global.getFacility().getCity());
                ps2.setString(5, Global.getFacility().getAddress());
                ps2.setString(6, Global.getFacility().getPhone());
                ps2.setString(7, Global.getFacility().getEmail());
                ps2.setInt(8, Global.getFacility().getId());
                ps2.execute();
            }
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static ObservableList<Facility> allFacilityList() {
        String sql = "SELECT * FROM facility_data_view order by city, name;";
        return facilityList(sql);
    }

    public static ObservableList<Facility> facilityList(String sql) {
        ObservableList<Facility> facilityList = FXCollections.observableArrayList();

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Facility facility = new Facility();
                setFacility(facility, rs);
                facilityList.add(facility);
            }
            return facilityList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static Facility getFacility(int facilityId) {
        String sql = "SELECT * FROM facility_data_view WHERE id_facility = " + facilityId + ";";

        Facility facility = new Facility();

        try (
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                setFacility(facility, rs);
            }
            return facility;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void setFacility(Facility facility, ResultSet rs) throws SQLException {
        facility.setId(rs.getInt("id_facility"));
        facility.setCode(rs.getString("code"));
        facility.setAddress(rs.getString("address"));
        facility.setCategory(rs.getString("category"));
        facility.setPhone(rs.getString("phone"));
        facility.setName(rs.getString("name"));
        facility.setCity(rs.getString("city"));
        facility.setEmail(rs.getString("email"));
        facility.setSchedule(rs.getString("schedule"));
        facility.setStatus(rs.getString("status"));
        facility.setNumber();
        facility.setFullAddress();
    }
}

























