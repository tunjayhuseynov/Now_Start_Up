package com.now.startupteamnow;

public class AnHistoryItem {
    private String mBonus;
    private String mText1;
    private String mText2;

    public AnHistoryItem(String bonus, String text1, String text2) {
        mBonus = bonus;
        mText1 = text1;
        mText2 = text2;
    }

    public String getmBonus() {
        return mBonus;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}
