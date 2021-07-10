package org.openjfx.ledicom.entities;

public class EmployeeContract {
    private String type;
    private String startDate;
    private String endDate;
    private int facilityId;
    private String facilityName;

    public EmployeeContract(String type, String startDate, String endDate) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return facilityName + " - " + type + " (" + startDate + " - " + endDate + ")";
    }

    public EmployeeContract() {

    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
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
