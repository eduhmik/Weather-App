package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.Current;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface WeatherRequests {
    @GET("{latitude},{longitude}")
    @Headers({ "Content-Type: application/x-www-form-urlencoded; charset=UTF-8", "Accept: application/json" })
    Call<ObjectResponse<Current>> getCurrentWeather(
            @Header("x-app-token") String token
    );

    @GET("{latitude},{longitude},{time}")
    @Headers({ "Content-Type: application/x-www-form-urlencoded; charset=UTF-8", "Accept: application/json" })
    Call<ObjectResponse<Current>> getPastFutureWeather(
            @Header("x-app-token") String token
    );
}
