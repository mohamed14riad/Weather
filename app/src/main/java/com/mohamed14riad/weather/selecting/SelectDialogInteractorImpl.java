package com.mohamed14riad.weather.selecting;

import android.content.Context;

public class SelectDialogInteractorImpl implements SelectDialogInteractor {
    private SelectStore store;

    public SelectDialogInteractorImpl(Context context) {
        this.store = new SelectStore(context);
    }

    @Override
    public String getSelectedCity() {
        return store.getSelectedCity();
    }

    @Override
    public void setSelectedCity(String city) {
        store.setSelectedCity(city);
    }
}
