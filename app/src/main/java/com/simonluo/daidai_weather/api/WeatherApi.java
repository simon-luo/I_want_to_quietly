package com.simonluo.daidai_weather.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.simonluo.daidai_weather.entity.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 333 on 2016/3/23.
 */
public class WeatherApi {
    @SerializedName("HeWeather data service 3.0")
    @Expose
    public List<Weather> mHeWeatherDataService30s = new ArrayList();
}
