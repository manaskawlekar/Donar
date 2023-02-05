package com.example.donate;

public class model2
{
    String fullname,email,phone,type;

   model2()
    {

    }

    public model2(String fullname,String email, String phone,String type){
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
