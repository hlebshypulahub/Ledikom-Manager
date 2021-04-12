package org.openjfx.utilities.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseNotificationController extends DatabaseController {

    public static void dobNotificationsPeriodEdit(int value) {
        String sql = "create or replace function dob_notifications_period()\n" +
                "    returns integer as\n" +
                "$$\n" +
                "begin\n" +
                "    return " + value + ";\n" +
                "end;\n" +
                "$$\n" +
                "    language 'plpgsql';";

        try (
//                Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static int getNotificationsPeriod(String sql) {
        int value = 30;

        try (
//                Connection conn = connect();
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

}
