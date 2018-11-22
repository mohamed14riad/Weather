package com.mohamed14riad.weather.selecting;

import android.content.Context;
import android.content.SharedPreferences;

public class SelectStore {
    private SharedPreferences pref;
    private static final String SELECTED_CITY = "selectedCity";
    private static final String PREF_NAME = "SelectStore";

    public SelectStore(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getSelectedCity() {
        return pref.getString(SELECTED_CITY, SelectType.CAIRO);
    }

    public void setSelectedCity(String selectedCity) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SELECTED_CITY, selectedCity);
        editor.apply();
    }
}
