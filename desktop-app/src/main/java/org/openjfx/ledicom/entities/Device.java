package org.openjfx.ledicom.entities;

public class Device {
    private int id;
    private Facility facility;
    private String type;
    private String name;
    private String verificationDate;
    private String nextVerificationDate;
    private String verificationInterval;
    private String location;
    private String number;
    private String temperatureRange;

    public Device() {
    }

    public Device(int id, Facility facility, String type, String name, String verificationDate, String nextVerificationDate, String verificationInterval, String location, String number, String temperatureRange) {
        this.id = id;
        this.facility = facility;
        this.type = type;
        this.name = name;
        this.verificationDate = verificationDate;
        this.nextVerificationDate = nextVerificationDate;
        this.verificationInterval = verificationInterval;
        this.location = location;
        this.number = number;
        this.temperatureRange = temperatureRange;
    }

    public Device(Facility facility, String type, String name, String verificationDate, String nextVerificationDate, String verificationInterval, String location, String number, String temperatureRange) {
        this.facility = facility;
        this.type = type;
        this.name = name;
        this.verificationDate = verificationDate;
        this.nextVerificationDate = nextVerificationDate;
        this.verificationInterval = verificationInterval;
        this.location = location;
        this.number = number;
        this.temperatureRange = temperatureRange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(String verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getNextVerificationDate() {
        return nextVerificationDate;
    }

    public void setNextVerificationDate(String nextVerificationDate) {
        this.nextVerificationDate = nextVerificationDate;
    }

    public String getVerificationInterval() {
        return verificationInterval;
    }

    public void setVerificationInterval(String verificationInterval) {
        this.verificationInterval = verificationInterval;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }
}
