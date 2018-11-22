package com.mohamed14riad.weather.listing;

import android.content.Context;

import com.mohamed14riad.weather.api.ApiClient;
import com.mohamed14riad.weather.api.WeatherApi;
import com.mohamed14riad.weather.pojo.Forecast;
import com.mohamed14riad.weather.pojo.WeatherInfo;
import com.mohamed14riad.weather.selecting.SelectStore;
import com.mohamed14riad.weather.selecting.SelectType;

import java.util.List;

import io.reactivex.Observable;

public class MainInteractorImpl implements MainInteractor {
    private WeatherApi weatherApi;
    private SelectStore store;


    public MainInteractorImpl(Context context) {
        weatherApi = ApiClient.getApiService();
        store = new SelectStore(context);
    }

    @Override
    public Observable<List<Forecast>> fetchWeatherInfo() {
        String city = store.getSelectedCity();

        switch (city) {
            case SelectType.CAIRO:
                return weatherApi.getWeatherInfo("cairo", "metric").map(WeatherInfo::getList);
            case SelectType.DUBAI:
                return weatherApi.getWeatherInfo("dubai", "metric").map(WeatherInfo::getList);
            case SelectType.PARIS:
                return weatherApi.getWeatherInfo("paris", "metric").map(WeatherInfo::getList);
            case SelectType.LONDON:
                return weatherApi.getWeatherInfo("london", "metric").map(WeatherInfo::getList);
            case SelectType.CANADA:
                return weatherApi.getWeatherInfo("canada", "metric").map(WeatherInfo::getList);
            default:
                return weatherApi.getWeatherInfo("cairo", "metric").map(WeatherInfo::getList);
        }
    }
}
