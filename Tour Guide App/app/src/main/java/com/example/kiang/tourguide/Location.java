package com.example.kiang.tourguide;

import static android.R.attr.description;
import static android.R.attr.name;
import static android.R.attr.phoneNumber;

public class Location {
    private String mName;
    private String mAddress;
    private String mHours;
    private String mPhoneNumber;
    private String mDescription;
    private String mLocation;
    private int mImageId = NO_IMAGE_ID;
    private static final int NO_IMAGE_ID = -1;

    public Location(String name, String location, int imageId){
        mName = name;
        mLocation = location;
        mImageId = imageId;
    }

    public Location(String name, String location, String address, String hours, String phoneNumber, String description, int imageId) {
        mName = name;
        mLocation = location;
        mAddress = address;
        mHours = hours;
        mPhoneNumber = phoneNumber;
        mDescription = description;
        mImageId = imageId;
    }

    public String getLocationName() {
        return this.mName;
    }

    public String getLocation() {
        return this.mLocation;
    }

    public boolean hasImage() {
        return this.mImageId != NO_IMAGE_ID;
    }

    public int getImageResourceId() {
        return this.mImageId;
    }
}
