
package com.example.brian.myrestaurants.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Coordinates {

    @SerializedName("latitude")
    @Expose
    Double latitude;
    @SerializedName("longitude")
    @Expose
    Double longitude;

    public Coordinates() {}

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
