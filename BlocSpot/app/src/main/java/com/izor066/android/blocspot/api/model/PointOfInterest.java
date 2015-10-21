package com.izor066.android.blocspot.api.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by igor on 22/9/15.
 */
public class PointOfInterest implements Parcelable {

    private final long rowId;
    private final String title;
    private final String address;
    private final float longitude;
    private final float latitude;
    private final String poiCategory;
    private final String poiNote;



    public PointOfInterest(long rowId, String title, String address, float latitude, float longitude, String poiCategory, String poiNote) {

        this.rowId = rowId;
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.poiCategory = poiCategory;
        this.poiNote = poiNote;

    }

    public long getRowId() {
        return rowId;
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

    public String getPoiCategory() {
        return poiCategory;
    }

    public String getPoiNote() {
        return poiNote;
    }


    @Override
    public String toString() {
        return "PointOfInterest{" +
                "rowId=" + rowId +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", poiCategory='" + poiCategory + '\'' +
                ", poiNote='" + poiNote + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putLong("rowId", rowId);
        bundle.putString("title", title);
        bundle.putString("address", address);
        bundle.putFloat("latitude", latitude);
        bundle.putFloat("longitude", longitude);
        bundle.putString("poiCategory", poiCategory);
        bundle.putString("poiNote", poiNote);

        dest.writeBundle(bundle);


    }

    public static final Parcelable.Creator<PointOfInterest> CREATOR = new Parcelable.Creator<PointOfInterest>() {
        public PointOfInterest createFromParcel(Parcel source){
            Bundle bundle = source.readBundle();

            return new PointOfInterest(bundle.getLong("rowId"),
                    bundle.getString("title"),
                    bundle.getString("address"),
                    bundle.getFloat("latitude"),
                    bundle.getFloat("longitude"),
                    bundle.getString("poiCategory"),
                    bundle.getString("poiNote"));
        }

        public PointOfInterest[] newArray(int size) {
            return new PointOfInterest[size];
        }
    };


}
