package com.now.startupteamnow;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

class CreateUser {
    @SerializedName("value")
    private Map<String, String> map = new HashMap<String, String>();

    private String PhoneNumber;

    private String Name;

    private String Surname;

    private String Date;

    private String ImgName;

    private boolean IsMale;

    private String Password;

    private String Email;

    Map<String, String> getMap() {
        return map;
    }


    String getPhoneNumber() {
        return PhoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        map.put("PhoneNumber", phoneNumber);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
        map.put("Name", name);
    }

    String getSurname() {
        return Surname;
    }

    void setSurname(String surname) {
        Surname = surname;
        map.put("Surname", surname);
    }

    String getDate() {
        return Date;
    }

    void setDate(String date) {
        Date = date;
        map.put("Date", date);
    }

    String getImgName() {
        return ImgName;
    }

    void setImgName(String imgName) {
        ImgName = imgName;
        map.put("ImgName", imgName);
    }

    public boolean isMale() {
        return IsMale;
    }

    void setMale(boolean male) {
        IsMale = male;
        map.put("IsMale", String.valueOf(male));
    }

    String getPassword() {
        return Password;
    }

    void setPassword(String password) {
        Password = password;
        map.put("Password", password);
    }

    String getEmail() {
        return Email;
    }

    void setEmail(String email) {
        Email = email;
        map.put("Email", email);
    }
}
