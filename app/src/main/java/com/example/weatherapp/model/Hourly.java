package com.example.weatherapp.model;

import java.util.ArrayList;

public class Hourly<T> {
    private String summary;
    private String icon;
    private String time;

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    private String temperature;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public ArrayList<T> data;
}
