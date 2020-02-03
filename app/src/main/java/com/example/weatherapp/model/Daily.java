package com.example.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily {
    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("summary")
    @Expose
    private String summary;
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
}
