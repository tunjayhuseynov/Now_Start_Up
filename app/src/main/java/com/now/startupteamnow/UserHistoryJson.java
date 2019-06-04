package com.now.startupteamnow;

import java.util.Date;

public class UserHistoryJson {

    private int UserId;
    private Date CapturedAt;
    private int Bonus;
    private String CompanyName;
    private int Id;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public Date getCapturedAt() {
        return CapturedAt;
    }

    public void setCapturedAt(Date capturedAt) {
        CapturedAt = capturedAt;
    }

    public int getBonus() {
        return Bonus;
    }

    public void setBonus(int bonus) {
        Bonus = bonus;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
