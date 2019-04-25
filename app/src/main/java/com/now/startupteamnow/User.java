package com.now.startupteamnow;

public class User {
    private int Id;
    private String Name;
    private String Surname;
    private String Date;
    private String ImgPath;
    private boolean IsMale;
    private int Bonus;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getDate() {
        return Date;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public boolean isMale() {
        return IsMale;
    }

    public int getBonus() {
        return Bonus;
    }
}
