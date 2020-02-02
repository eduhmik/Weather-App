package com.example.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hourly {
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<Datum> getData() {
        return data;
    }
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("apparentTemperature")
    @Expose
    private Double apparentTemperature;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("windSpeed")
    @Expose
    private Double windSpeed;
    @SerializedName("uvIndex")
    @Expose
    private Integer uvIndex;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;

    public Long getTime() {
        return time;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    public Integer getVisibility() {
        return visibility;
    }
}
