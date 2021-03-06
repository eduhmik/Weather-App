package com.example.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("apparentTemperature")
    @Expose
    private Double apparentTemperature;

    public Double getApparentTemperatureHigh() {
        return apparentTemperatureHigh;
    }

    @SerializedName("apparentTemperatureHigh")
    @Expose
    private Double apparentTemperatureHigh;

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
    private Double visibility;

    public Long getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
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

    public Double getVisibility() {
        return visibility;
    }
}
