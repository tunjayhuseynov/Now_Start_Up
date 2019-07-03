package com.now.startupteamnow;

class AnItem {
    private String mImageResource;
    private String mText1;
    private String mText2;

    AnItem(String imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    String getImageResource() {
        return mImageResource;
    }

    String getText1() {
        return mText1;
    }

    String getText2() {
        return mText2;
    }
}