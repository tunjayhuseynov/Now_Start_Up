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

    public String getCode() {
        return Code;
    }

    public int getBonus() {
        return Bonus;
    }

    public double getxCoordination() {
        return xCoordination;
    }

    public double getyCoordination() {
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
}
