package org.openjfx.utilities.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseController {
    //---------- external
//    private static final String dbURL = "jdbc:postgresql://82.209.251.148:60555/inventory";
//    private static final String dbUser = "inventory_user";
//    private static final String dbPassword = "o5EOEPnCEvqN";
    //---------- internal
//    private static final String dbURL = "jdbc:postgresql://localhost:60555/inventory";
//    private static final String dbUser = "inventory_user";
//    private static final String dbPassword = "o5EOEPnCEvqN";
    //---------- my
    private static final String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String dbUser = "postgres";
    private static final String dbPassword = "postgres";
    protected static Connection conn = connect();

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.print("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }

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
