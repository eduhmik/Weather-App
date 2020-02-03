package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.Currently;
import com.example.weatherapp.model.Daily;
import com.example.weatherapp.model.Hourly;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectResponse<T> {
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("currently")
    @Expose
    private Currently currently;

    public Daily getDaily() {
        return daily;
    }

    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("hourly")
    @Expose
    private Hourly hourly;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public Integer getOffset() {
        return offset;
    }
}
