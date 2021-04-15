package org.openjfx.utilities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseController {
    private static final String dbURL = "jdbc:postgresql://localhost:5432/ledikom";
    private static final String dbUser = "ledikom";
    private static final String dbPassword = "ledikom";
    protected static Connection conn = connect();

    public static Connection connect() {
        long startTime = System.nanoTime();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.print("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println(" | " + (double) elapsedTime / 1000000 + " ms");

        return conn;
    }

    public static void psExecute(String sql) throws SQLException {
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void close() throws SQLException {
        conn.close();
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        DatabaseController.conn = conn;
    }
}
