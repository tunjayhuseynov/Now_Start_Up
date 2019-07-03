package com.now.startupteamnow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("isFound")
    @Expose
    private boolean isFound;
    @SerializedName("isPassCorrect")
    @Expose
    private boolean isPassCorrect;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    boolean isPassCorrect() {
        return isPassCorrect;
    }

    public void setPassCorrect(boolean passCorrect) {
        isPassCorrect = passCorrect;
    }
}
