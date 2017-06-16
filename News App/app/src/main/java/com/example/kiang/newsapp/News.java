package com.example.kiang.newsapp;

import android.graphics.Bitmap;

import static android.R.attr.bitmap;

/**
 * An {@link News} object contains information related to a single book.
 */

public class News {
    private String mTitle;
    private Bitmap mBitmap;
    private String mWebUrl;
    public News(String mTitle, Bitmap bitmap, String mWebUrl) {
        this.mTitle = mTitle;
        this.mBitmap = bitmap;
        this.mWebUrl = mWebUrl;
    }

    public String getTitle() {
        return mTitle;
    }
    public Bitmap getBitmap() {
        return mBitmap;
    }
    public String getWebUrl(){return mWebUrl;}
}
