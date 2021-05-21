package org.openjfx.utilities.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        int value = 30;

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
}
