package com.example.alexandreroussiere.bmwlocation.webService.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Model used to fetch the data from the API
 */

public class LocationInfo implements Parcelable {

    @SerializedName("ID")
    private long id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Latitude")
    private float latitude;
    @SerializedName("Longitude")
    private float longitude;
    @SerializedName("Address")
    private String address;
    @SerializedName("ArrivalTime")
    private String arrivalTime;

    public LocationInfo() {
    }

    public LocationInfo(Parcel in) {
        id = in.readLong();
        name = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
        address = in.readString();
        arrivalTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeFloat(this.latitude);
        dest.writeFloat(this.longitude);
        dest.writeString(this.address);
        dest.writeString(this.arrivalTime);
    }

    public static final Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel source) {
            return new LocationInfo(source);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };

    public long getID() {
        return id;
    }

    public void setID(long ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


}
