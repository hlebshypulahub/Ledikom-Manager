package org.openjfx.utilities.converters;

import java.sql.Date;

public class SqlDateStringConverter {
    public static String sqlDateToString(Date date) {
        if(date == null) {
            return null;
        } else {
            String[] parts = date.toString().split("-");
            return parts[2] + "." + parts[1] + "." + parts[0];
        }
    }

    public static Date stringToSqlDate(String date) {
        if(date != null) {
            String[] parts = date.split("\\.");
            return Date.valueOf(parts[2] + "-" + parts[1] + "-" + parts[0]);
        } else {
            return null;
        }
    }
}
