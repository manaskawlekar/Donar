package com.example.donate;

public class model
{
    String name,NGOcode,email,phone,type,id;
    model()
    {

    }
    public model(String name,String NGOcode, String email, String phone, String type, String id) {
        this.name = name;
        this.NGOcode = NGOcode;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNGOcode() {
        return NGOcode;
    }
    public void setNGOcode(String NGOcode) {
        this.NGOcode = NGOcode;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
