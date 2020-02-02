package com.example.weatherapp.model;

public class Current {
    private String summary;
    private String icon;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    private String temperature;
    private String humidity;
    private String windSpeed;
    private String visibility;
    private String uvIndex;
}
