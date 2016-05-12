package com.deserteaglefe.seventhweek;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zyl on 2016/5/11.
 */
public class Location implements Parcelable {
    private double mLatitude;
    private double mLongitude;

    public Location(){
        mLatitude = 0;
        mLongitude = 0;
    }
    public Location(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    protected Location(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }
}
