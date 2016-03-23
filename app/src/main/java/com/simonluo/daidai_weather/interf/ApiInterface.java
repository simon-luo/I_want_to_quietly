package com.simonluo.daidai_weather.interf;

import com.simonluo.daidai_weather.api.WeatherApi;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by 333 on 2016/3/22.
 */
public interface ApiInterface {
    String API = "https://api.heweather.com/x3/";
    @GET("weather") Observable<WeatherApi> mWeatherApi(@Query("city") String city, @Query("key") String key);
}
