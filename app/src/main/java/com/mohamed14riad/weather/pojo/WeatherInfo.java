package com.mohamed14riad.weather.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {

    @SerializedName("list")
    @Expose
    private List<Forecast> list = null;

    public WeatherInfo() {
    }

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }
}
