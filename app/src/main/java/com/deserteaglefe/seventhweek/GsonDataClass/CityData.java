package com.deserteaglefe.seventhweek.GsonDataClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by zyl on 2016/4/16.
 */
public class CityData {

    @SerializedName("status")
    private String status;

    @SerializedName("city_info")
    private List<CityInfo> city_info;

    public static CityData objectFromData(String str) {

        return new Gson().fromJson(str, CityData.class);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CityInfo> getCity_info() {
        return city_info;
    }

    public void setCity_info(List<CityInfo> city_info) {
        this.city_info = city_info;
    }

    public static class CityInfo implements Parcelable, Serializable {

        @SerializedName("city")
        private String city;

        @SerializedName("id")
        private String id;

        @SerializedName("lat")
        private float lat;

        @SerializedName("lon")
        private float lon;

        @SerializedName("prov")
        private String prov;

        protected CityInfo(){

        }
        protected CityInfo(Parcel in) {
            city = in.readString();
            id = in.readString();
            lat = in.readFloat();
            lon = in.readFloat();
            prov = in.readString();
        }

        public static final Creator<CityInfo> CREATOR = new Creator<CityInfo>() {
            @Override
            public CityInfo createFromParcel(Parcel in) {
                return new CityInfo(in);
            }

            @Override
            public CityInfo[] newArray(int size) {
                return new CityInfo[size];
            }
        };

        public static CityInfo objectFromData(String str) {

            return new Gson().fromJson(str, CityInfo.class);
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        @Override
        public String toString() {
            return getCity();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(city);
            dest.writeString(id);
            dest.writeFloat(lat);
            dest.writeFloat(lon);
            dest.writeString(prov);
        }
    }
}
