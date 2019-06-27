package com.now.startupteamnow;

public class QRcode {

    private String Code;
    private int Bonus;
    private double xCoordination;
    private double yCoordination;
    private String CompanyName;
    private String Details;
    private boolean BonusType;
    private String Image;
    private int Id;

    public int getId() {
        return Id;
    }

    String getCode() {
        return Code;
    }

    int getBonus() {
        return Bonus;
    }

    double getxCoordination() {
        return xCoordination;
    }

    double getyCoordination() {
        return yCoordination;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getDetails() {
        return Details;
    }

    public boolean isBonusType() {
        return BonusType;
    }

    public String getImage() {
        return Image;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setBonus(int bonus) {
        Bonus = bonus;
    }

    public void setxCoordination(double xCoordination) {
        this.xCoordination = xCoordination;
    }

    public void setyCoordination(double yCoordination) {
        this.yCoordination = yCoordination;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public void setBonusType(boolean bonusType) {
        BonusType = bonusType;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setId(int id) {
        Id = id;
    }
}
