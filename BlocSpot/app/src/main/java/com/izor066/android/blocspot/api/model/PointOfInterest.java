package com.izor066.android.blocspot.api.model;

/**
 * Created by igor on 22/9/15.
 */
public class PointOfInterest {

    private final String title;
    private final String address;
    private final float longitude;
    private final float latitude;

    public PointOfInterest(String title, String address, float latitude, float longitude) {

        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }



    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }


}
