package com.example.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.viewholders.HourlyViewHolder;
import com.example.weatherapp.model.Datum;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyViewHolder> {

    private Context context;
    private ArrayList<Datum> datumArrayList;


    public HourlyAdapter(Context context, ArrayList datumArrayList) {
        this.datumArrayList = datumArrayList;
        this.context = context;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_layout_item, parent, false);
        return new HourlyViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final HourlyViewHolder holder, int position) {
        holder.bind(datumArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }
}
