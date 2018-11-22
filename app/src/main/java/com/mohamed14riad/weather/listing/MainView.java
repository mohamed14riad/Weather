package com.mohamed14riad.weather.listing;

import com.mohamed14riad.weather.pojo.Forecast;

import java.util.List;

public interface MainView {
    void showWeatherInfo(List<Forecast> forecastList);

    void showLoadingIndicator();

    void showFailed(String errorMessage);
}
