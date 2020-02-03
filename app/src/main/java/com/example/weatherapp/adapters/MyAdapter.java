package com.example.weatherapp.adapters;

import android.graphics.RectF;

import com.robinhood.spark.SparkAdapter;

public class MyAdapter extends SparkAdapter {

    float[] yData;
    public MyAdapter(float[] yData) {
        this.yData = yData;
    }
    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }

    @Override
    public RectF getDataBounds() {
        RectF bounds = super.getDataBounds();
        // will 'zoom in' to the middle portion of the graph
        bounds.inset(bounds.width() / 4, bounds.height() / 4);
        return bounds;
    }
}
