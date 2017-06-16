package com.example.kiang.booklistingapp;

import android.graphics.Bitmap;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDescription;
    private double mRating;
    private String mThumbnail;
    private String mUrl;
    private Bitmap mBitmap;

    public Book(String Author, String mDescription, double mRating, String mThumbnail, String mTitle, String mUrl, Bitmap mBitmap) {
        this.mAuthor = Author;
        this.mDescription = mDescription;
        this.mRating = mRating;
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mBitmap = mBitmap;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getRating() {
        return mRating;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }
}
