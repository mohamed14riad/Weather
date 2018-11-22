package com.mohamed14riad.weather.listing;

import com.mohamed14riad.weather.pojo.Forecast;

import java.util.List;

import io.reactivex.Observable;

public interface MainInteractor {
    Observable<List<Forecast>> fetchWeatherInfo();
}
