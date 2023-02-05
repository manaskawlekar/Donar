package com.example.donate;

public class ClothDonation {
    private String name;
    private String address;
    private String phone;
    private String quantity;
    private String descriptions;
    private String clothType;

    public ClothDonation(String name, String address, String phone, String quantity, String descriptions, String clothType) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.quantity = quantity;
        this.descriptions = descriptions;
        this.clothType = clothType;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getphone() {

        return phone;
    }

    public String getQuantity() {
        return quantity;
    }
    public String getDescriptions() {
        return descriptions;
    }

    public String getclothType() {
        return clothType;
    }
}