package com.example.weatherapp.adapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.Datum;

import java.text.SimpleDateFormat;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    Datum datum;
    @BindView(R.id.tv_hourly)
    TextView tvHourly;
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.tv_hourly_temperature)
    TextView tvHourlyTemperature;

    public HourlyViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Datum datum) {
        this.datum = datum;
        Long time = datum.getTime();
        Long timed = time * 1000;
        String temperature = String.valueOf(datum.getApparentTemperature());
        SimpleDateFormat postFormater = new SimpleDateFormat("HH:mm");
        String newDateStr = postFormater.format(timed);
        tvHourly.setText(newDateStr);
        tvHourlyTemperature.setText(temperature+"\u2109");
    }
}
