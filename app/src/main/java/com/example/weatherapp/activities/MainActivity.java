package com.example.weatherapp.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.HourlyAdapter;
import com.example.weatherapp.adapters.MyAdapter;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.model.Currently;
import com.example.weatherapp.model.Datum;
import com.example.weatherapp.retrofit.ObjectResponse;
import com.example.weatherapp.retrofit.ServiceGenerator;
import com.example.weatherapp.retrofit.WeatherRequests;
import com.example.weatherapp.tools.SingleShotLocationProvider;
import com.example.weatherapp.tools.SweetAlertDialog;
import com.robinhood.spark.SparkView;
import com.robinhood.spark.animation.MorphSparkAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected LocationListener locationListener;
    protected LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60 * 1000 * 1; // 1 minute
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    public Calendar calendarDate, calendarTime;
    long time;
    String lat;
    String longitude1;
    public boolean pickDateSet, pickTimeSet;
    private HourlyAdapter hourlyAdapter;
    private ArrayList<Datum> datumArrayList = new ArrayList<>();
    private ArrayList<Datum> dailyArrayList = new ArrayList<>();
    List<Float> myList = new ArrayList<Float>();
    MorphSparkAnimator sparkAnimator;
    float[] arr = new float[0];
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_select)
    AppCompatButton btnSelect;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_wind)
    TextView tvWind;
    @BindView(R.id.tv_visibility)
    TextView tvVisibility;
    @BindView(R.id.tv_uv)
    TextView tvUv;
    @BindView(R.id.hourly_recyclerview)
    RecyclerView hourlyRecyclerview;
    @BindView(R.id.sparkview)
    SparkView sparkview;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        hourlyRecyclerview.setLayoutManager(layoutManager);
        checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
        SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        lat = String.valueOf(location.latitude);
                        longitude1 = String.valueOf(location.longitude);
                        fetchWeatherDetails(lat, longitude1);
                    }
                });
    }

    @OnClick(R.id.btn_select)
    public void onViewClicked() {
        pickDate(1);
    }

    // Fetch the weather details after getting the location details
    public void fetchWeatherDetails(String latitude, String longitude) {
        showSweetDialog("Weather Details", "Loading. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        WeatherRequests service = ServiceGenerator.createService(WeatherRequests.class);
        Call<ObjectResponse<Currently>> call = service.getCurrentWeather(latitude, longitude);
        call.enqueue(new Callback<ObjectResponse<Currently>>() {
            @Override
            public void onResponse(Call<ObjectResponse<Currently>> call, Response<ObjectResponse<Currently>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null && response.isSuccessful()) {
                    showSweetDialog("Successful!!!", "Fetched details successfully", SweetAlertDialog.SUCCESS_TYPE);
                    _sweetAlertDialog.dismissWithAnimation();
                    String timezone = response.body().getTimezone();
                    String temperature = String.valueOf(response.body().getCurrently().getApparentTemperature());
                    String summary = response.body().getCurrently().getSummary();
                    Long time = response.body().getCurrently().getTime();
                    Long timed = time * 1000;
                    SimpleDateFormat postFormater = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                    String newDateStr = postFormater.format(timed);
                    String humidity = String.valueOf(response.body().getCurrently().getHumidity());
                    String wind = String.valueOf(response.body().getCurrently().getWindSpeed());
                    String visibility = String.valueOf(response.body().getCurrently().getVisibility());
                    String uv = String.valueOf(response.body().getCurrently().getUvIndex());
                    tvLocation.setText(timezone);
                    tvTemperature.setText(temperature+"\u2109");
                    tvSummary.setText(summary);
                    tvDate.setText(newDateStr);
                    tvHumidity.setText(humidity);
                    tvWind.setText(wind);
                    tvVisibility.setText(visibility);
                    tvUv.setText(uv);

                    // set data on the hourly recyclerview
                    try {
                        if (response.body() != null && response.isSuccessful()) {
                            ArrayList<Datum> resp = response.body().getHourly().getData();
                            ArrayList<Datum> responze = response.body().getDaily().getData();
                            dailyArrayList.addAll(responze);
                            for (Datum datum : dailyArrayList) {
                                double dailyApparentTemperature = datum.getApparentTemperatureHigh();
                                myList.add((float) dailyApparentTemperature);
                                arr = new float[myList.size()];
                                int index = 0;
                                for (final Float value: myList) {
                                    arr[index++] = value;
                                }
                            }
                            datumArrayList.clear();
                            datumArrayList.addAll(resp);
                            hourlyAdapter = new HourlyAdapter(getBaseContext(), datumArrayList);
                            sparkview.setAdapter(new MyAdapter(arr));
                            sparkview.getBaseline();
                            sparkview.setSparkAnimator(sparkAnimator);
                            sparkview.setScrubEnabled(true);
                            hourlyRecyclerview.setAdapter(hourlyAdapter);
                        } else {
                            showToast("Please try again");
                        }
                    } catch (Exception e) {
                        datumArrayList.clear();
                        hourlyAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }
                } else {
                    showToast("No response from server");
                    showSweetDialog("Failed!", "Fetching weather details failed", SweetAlertDialog.ERROR_TYPE, "Got it!",new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse<Currently>> call, Throwable t) {
                Log.e("", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    // This is a method to pick a date and view the weather details in the past or present.
    // The time picked is set into the TimeMachineRequest that returns data specific for that day

    private void pickDate(int i) {
        if (i == 1) {
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            pickDateSet = true;
                            calendarDate = Calendar.getInstance();
                            calendarDate.set(Calendar.YEAR, year);
                            calendarDate.set(Calendar.MONTH, month);
                            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            time = calendarDate.getTimeInMillis()/1000L;
                            SimpleDateFormat postFormater = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                            String newDateStr = postFormater.format(time);
                            tvDate.setText(newDateStr);
                            fetchPastOrFutureWeatherDetails(lat, longitude1, time);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    // Fetch the weather details after getting the location details for previous or future date
    // Here we pass a time in millis parameter which is then passed in our url and used to fetch whether in future or past
    public void fetchPastOrFutureWeatherDetails(String latitude, String longitude, Long time) {
        showSweetDialog("Weather Details", "Loading. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        WeatherRequests service = ServiceGenerator.createService(WeatherRequests.class);
        Call<ObjectResponse<Currently>> call = service.getPastFutureWeather(latitude, longitude, time);
        call.enqueue(new Callback<ObjectResponse<Currently>>() {
            @Override
            public void onResponse(Call<ObjectResponse<Currently>> call, Response<ObjectResponse<Currently>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null && response.isSuccessful()) {
                    showSweetDialog("Successful!!!", "Fetched details successfully", SweetAlertDialog.SUCCESS_TYPE);
                    _sweetAlertDialog.dismissWithAnimation();
                    String timezone = response.body().getTimezone();
                    String temperature = String.valueOf(response.body().getCurrently().getApparentTemperature());
                    String summary = response.body().getCurrently().getSummary();
                    Long time = response.body().getCurrently().getTime();
                    Long timed = time * 1000;
                    SimpleDateFormat postFormater = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                    String newDateStr = postFormater.format(timed);
                    String humidity = String.valueOf(response.body().getCurrently().getHumidity());
                    String wind = String.valueOf(response.body().getCurrently().getWindSpeed());
                    String visibility = String.valueOf(response.body().getCurrently().getVisibility());
                    String uv = String.valueOf(response.body().getCurrently().getUvIndex());
                    tvLocation.setText(timezone);
                    tvTemperature.setText(temperature+"\u2109");
                    tvSummary.setText(summary);
                    tvDate.setText(newDateStr);
                    tvHumidity.setText(humidity);
                    tvWind.setText(wind);
                    tvVisibility.setText(visibility);
                    tvUv.setText(uv);

                    // set data on the hourly recyclerview
                    try {
                        if (response.body() != null && response.isSuccessful()) {
                            ArrayList<Datum> resp = response.body().getHourly().getData();
                            datumArrayList.clear();
                            datumArrayList.addAll(resp);
                            hourlyAdapter = new HourlyAdapter(getBaseContext(), datumArrayList);
                            hourlyRecyclerview.setAdapter(hourlyAdapter);
                        } else {
                            showToast("Please try again");
                        }
                    } catch (Exception e) {
                        datumArrayList.clear();
                        hourlyAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }
                } else {
                    showToast("No response from server");
                    showSweetDialog("Failed!", "Fetching weather details failed", SweetAlertDialog.ERROR_TYPE, "Got it!",new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse<Currently>> call, Throwable t) {
                Log.e("", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }
}
