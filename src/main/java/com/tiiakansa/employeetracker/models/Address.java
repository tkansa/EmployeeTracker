package com.tiiakansa.employeetracker.models;

public class Address {

    private String street;
    private String city;
    private String region;
    private String postal;
    private String country;

    public Address(){};

    public Address(String street, String city, String region, String postal, String country) {
        this.street = street;
        this.city = city;
        this.region = region;
        this.postal = postal;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
