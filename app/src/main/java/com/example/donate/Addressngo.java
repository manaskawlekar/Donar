package com.example.donate;

public class Addressngo {
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String landmark;

    public Addressngo(String streetAddress, String city, String state, String postalCode, String country, String landmark) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.landmark = landmark;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getLandmark() {
        return landmark;
    }
}
