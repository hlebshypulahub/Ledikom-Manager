package org.openjfx.ledicom.entities;

import org.openjfx.utilities.formatters.DateFormatter;

import java.time.LocalDate;

public class EmployeeContract {
    private String type;
    private String startDate;
    private String endDate;

    public EmployeeContract(String type, LocalDate startDate, LocalDate endDate) {
        this.type = type;
        this.startDate = DateFormatter.format(startDate);
        this.endDate = DateFormatter.format(endDate);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
