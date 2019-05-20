package com.now.startupteamnow;

import java.util.HashMap;
import java.util.Map;

class CreateUser {
    private Map<String, String> map = new HashMap<String, String>();

    private String PhoneNumber;

    private String Name;

    private String Surname;

    private String Date;

    private String ImgName;

    private boolean IsMale;

    private String Password;

    private String Email;

    public Map<String, String> getMap() {
        return map;
    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
        map.put("Surname", surname);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
        map.put("Date", date);
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
        map.put("ImgName", imgName);
    }

    public boolean isMale() {
        return IsMale;
    }

    public void setMale(boolean male) {
        IsMale = male;
        map.put("IsMale", String.valueOf(male));
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
        map.put("Password", password);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
        map.put("Email", email);
    }
}
