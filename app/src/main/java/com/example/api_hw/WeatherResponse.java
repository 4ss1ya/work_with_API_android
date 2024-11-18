package com.example.api_hw;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("main")
    private Main main;

    @SerializedName("name")
    private String name;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("sys")
    private Sys sys;

    public double getTemp() {
        return main != null ? main.temp : 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return weatherList != null && !weatherList.isEmpty() ? weatherList.get(0).description : "";
    }

    public String getCountry() {
        return sys != null ? sys.country : "";
    }

    private static class Main {
        @SerializedName("temp")
        double temp;
    }

    private static class Weather {
        @SerializedName("description")
        String description;
    }

    private static class Sys {
        @SerializedName("country")
        String country;
    }
}
