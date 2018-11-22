package com.mohamed14riad.weather.api;

import com.mohamed14riad.weather.pojo.WeatherInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast")
    Observable<WeatherInfo> getWeatherInfo(@Query("q") String cityName, @Query("units") String tempUnit);
}
