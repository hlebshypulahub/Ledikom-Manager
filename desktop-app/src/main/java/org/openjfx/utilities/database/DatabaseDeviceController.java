package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.ledicom.entities.Facility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseDeviceController extends DatabaseController {

    public static void addDevice(Device device) throws SQLException {
        String sql = "insert into device (id_facility, type, name, verification_date, next_verification_date, verification_interval, location, number, temperature_range)"
                + "values (" + (device.getFacility().getId() == -1 ? "null" : device.getFacility().getId())
                + ",'" + device.getType()
                + "','" + device.getName()
                + "','" + device.getVerificationDate()
                + "','" +
                device.getNextVerificationDate() + "','" + device.getVerificationInterval() + "','" + device.getLocation() + "','" + device.getNumber() + "','" + device.getTemperatureRange() + "');";

        psExecute(sql);
    }

    public static ObservableList<Device> getDevices() throws SQLException {
        String sql = "select d.name as dname, *, f.id_facility as fid, f.name as fname from device d " +
                "left join facility f on d.id_facility = f.id_facility order by right(d.next_verification_date, 4), fid;";

        ObservableList<Device> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(new Device(rs.getInt("id_device"),
                        rs.getInt("fid") == 0 ? new Facility("Резервные средства измерений", -1) : new Facility(rs.getString("fname"), rs.getInt("fid")),
                        rs.getString("type"), rs.getString("dname"), rs.getString("verification_date"), rs.getString("next_verification_date"),
                        rs.getString("verification_interval"), rs.getString("location"), rs.getString("number"), rs.getString("temperature_range")));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static ObservableList<Device> getDevices(Facility facility, String year) throws SQLException {
        String sql;

        if (facility == null && year == null)
            sql = "select d.name as dname, *, f.id_facility as fid, f.name as fname from device d " +
                    "left join facility f on d.id_facility = f.id_facility order by right(d.next_verification_date, 4), fid;";
        else if (year == null)
            sql = "select d.name as dname, *, f.id_facility as fid, f.name as fname from device d " +
                    "left join facility f on d.id_facility = f.id_facility where f.name " + (facility.getName().equals("Резервные средства измерений") ? "is null" : "= '" + facility.getName() + "'") + " order by right(d.next_verification_date, 4), fid;";
        else if (facility == null)
            sql = "select d.name as dname, *, f.id_facility as fid, f.name as fname from device d " +
                    "left join facility f on d.id_facility = f.id_facility where right(d.next_verification_date, 4) = '" + year + "' order by right(d.next_verification_date, 4), fid;";
        else
            sql = "select d.name as dname, *, f.id_facility as fid, f.name as fname from device d " +
                    "left join facility f on d.id_facility = f.id_facility where right(d.next_verification_date, 4) = '" + year
                    + "' and f.name " + (facility.getName().equals("Резервные средства измерений") ? "is null" : "= '" + facility.getName() + "'") + " order by right(d.next_verification_date, 4), fid;";

        ObservableList<Device> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(new Device(rs.getInt("id_device"),
                        rs.getInt("fid") == 0 ? new Facility("Резервные средства измерений", -1) : new Facility(rs.getString("fname"), rs.getInt("fid")),
                        rs.getString("type"), rs.getString("dname"), rs.getString("verification_date"), rs.getString("next_verification_date"),
                        rs.getString("verification_interval"), rs.getString("location"), rs.getString("number"), rs.getString("temperature_range")));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static void deleteDevice(Device device) throws SQLException {
        String sql = "delete from device where id_device = " + device.getId();

        psExecute(sql);
    }

    public static void editDevice(Device device) throws SQLException {
        String sql = "update device set id_facility = " + (device.getFacility().getId() == -1 ? "null" : device.getFacility().getId())
                + ", type = '" + device.getType() + "', name = '" + device.getName() + "', verification_date = '" + device.getVerificationDate() + "', next_verification_date = '" + device.getNextVerificationDate()
                + "', verification_interval = '" + device.getVerificationInterval() + "', location = '" + device.getLocation() + "', number = '" + device.getNumber() + "', temperature_range = '" + device.getTemperatureRange() + "' "
                + "where id_device = " + device.getId() + ";";

        psExecute(sql);
    }
}
