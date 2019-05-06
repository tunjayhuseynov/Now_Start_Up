package com.now.startupteamnow;

public class User {
    private int Id;
    private String Name;
    private String Surname;
    private String Date;
    private String ImgName;
    private boolean IsMale;
    private int Bonus;
    private String Token;

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
        return ImgName;
    }

    public boolean isMale() {
        return IsMale;
    }

    public int getBonus() {
        return Bonus;
    }

    public String getToken(){ return Token;}
}
