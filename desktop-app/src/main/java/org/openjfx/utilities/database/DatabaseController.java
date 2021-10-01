package org.openjfx.utilities.database;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseController {
    protected static Connection conn = connect();

    public static Connection connect() {
        String propFileName = "config.properties";

        String dbURL;
        String dbUser;
        String dbPassword;

        Connection conn = null;

        try (InputStream inputStream = DatabaseController.class.getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

//            /// EXTERNAL
            dbURL = prop.getProperty("externalURL");
            dbUser = prop.getProperty("externalUser");
            dbPassword = prop.getProperty("externalPassword");
//            /// INTERNAL
//            dbURL = prop.getProperty("internalURL");
//            dbUser = prop.getProperty("internalUser");
//            dbPassword = prop.getProperty("internalPassword");
            /// LOCAL
//            dbURL = prop.getProperty("localURL");
//            dbUser = prop.getProperty("localUser");
//            dbPassword = prop.getProperty("localPassword");

            try {
                conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                System.out.print("Connected to the PostgreSQL server successfully\n");
            } catch (SQLException e) {
                showMessageDialog(null, e.getMessage());
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
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

    public static int getActualVersion() {
        String sql = "select * from app_version();";

        int version = 0;

        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                version = rs.getInt("app_version");
            }
            return version;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return version;
        }
    }
}
