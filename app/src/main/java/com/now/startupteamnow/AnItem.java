package com.now.startupteamnow;

public class AnItem {
    private String mImageResource;
    private String mText1;
    private String mText2;

    public AnItem(String imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public String getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}