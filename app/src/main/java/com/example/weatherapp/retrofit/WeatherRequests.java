package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.Currently;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WeatherRequests {
    @GET("{latitude},{longitude}")
    @Headers({ "Content-Type: application/x-www-form-urlencoded; charset=UTF-8", "Accept: application/json" })
    Call<ObjectResponse<Currently>> getCurrentWeather(
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );

    @GET("{latitude},{longitude},{time}")
    @Headers({ "Content-Type: application/x-www-form-urlencoded; charset=UTF-8", "Accept: application/json" })
    Call<ObjectResponse<Currently>> getPastFutureWeather(
            @Path("latitude") String latitude,
            @Path("longitude") String longitude,
            @Path("time") Long time
    );
}
