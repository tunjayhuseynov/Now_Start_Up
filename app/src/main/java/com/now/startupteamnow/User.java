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

    String getSurname() {
        return Surname;
    }

    public String getDate() {
        return Date;
    }

    String getImgPath() {
        return ImgName;
    }

    public boolean isMale() {
        return IsMale;
    }

    int getBonus() {
        return Bonus;
    }

    String getToken(){ return Token;}
}
