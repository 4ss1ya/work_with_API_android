package com.example.api_hw;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiKey
    );
}
