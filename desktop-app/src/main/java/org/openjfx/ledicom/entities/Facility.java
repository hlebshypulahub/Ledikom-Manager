package org.openjfx.ledicom.entities;

public class Facility {
    String name;
    String status;
    String category;
    String schedule;
    String city;
    String address;
    String fullAddress;
    String phone;
    String email;
    String code;
    int id;
    int number;

    public Facility() {
    }

    public Facility(String name, String status, String category, String schedule, String city, String address, String phone, String email) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.schedule = schedule;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fullAddress = city + ", " + address;
        setNumber();
    }

    public Facility(int id, String name, String status, String category, String schedule, String city, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.schedule = schedule;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fullAddress = city + ", " + address;
        setNumber();
    }

    public Facility(String name, String status, String category, String schedule, String city, String address, String phone, String email, String code) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.schedule = schedule;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.code = code;
        this.fullAddress = city + ", " + address;
        setNumber();
    }

    public Facility(int id, String name, String status, String category, String schedule, String city, String address, String phone, String email, String code) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.schedule = schedule;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.code = code;
        this.fullAddress = city + ", " + address;
        setNumber();
    }

    public Facility(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setNumber() {
        String temp = name;
        number = temp.replaceAll("\\D+","").isEmpty() ? 0 : Integer.parseInt(temp.replaceAll("\\D+",""));
    }

    public int getNumber() {
        return number;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress() {
        if (city.isEmpty() && address.isEmpty()) {
            this.fullAddress = "";
        } else if (address.isEmpty()) {
            this.fullAddress = city;
        } else if (city.isEmpty()) {
            this.fullAddress = address;
        } else {
            this.fullAddress = city + ", " + address;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
