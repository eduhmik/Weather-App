package com.example.weatherapp.retrofit;

public class ObjectResponse<T> {
    public String getTimezone() {
        return timezone;
    }

    public String getCurrently() {
        return currently;
    }

    private String timezone;
    private String currently;
    private String hourly;
}
