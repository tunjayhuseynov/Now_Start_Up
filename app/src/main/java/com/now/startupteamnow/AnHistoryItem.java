package com.now.startupteamnow;

class AnHistoryItem {
    private String mBonus;
    private String mText1;
    private String mText2;

    AnHistoryItem(String bonus, String text1, String text2) {
        mBonus = bonus;
        mText1 = text1;
        mText2 = text2;
    }

    String getmBonus() {
        return mBonus;
    }

    String getText1() {
        return mText1;
    }

    String getText2() {
        return mText2;
    }
}
