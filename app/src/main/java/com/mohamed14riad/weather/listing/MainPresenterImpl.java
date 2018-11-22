package com.mohamed14riad.weather.listing;

import android.content.Context;

import com.mohamed14riad.weather.pojo.Forecast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private MainInteractor interactor;
    private Disposable fetchSubscription;

    public MainPresenterImpl(Context context) {
        interactor = new MainInteractorImpl(context);
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private void showLoading() {
        if (isViewAttached()) {
            view.showLoadingIndicator();
        }
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
        showInfo();
    }

    @Override
    public void showInfo() {
        showLoading();

        fetchSubscription = interactor.fetchWeatherInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onWeatherInfoFetchSuccess, this::onWeatherInfoFetchFailed);
    }

    @Override
    public void destroy() {
        view = null;

        if (fetchSubscription != null && !fetchSubscription.isDisposed()) {
            fetchSubscription.dispose();
        }
    }

    private void onWeatherInfoFetchSuccess(List<Forecast> forecastList) {
        if (isViewAttached()) {
            view.showWeatherInfo(forecastList);
        }
    }

    private void onWeatherInfoFetchFailed(Throwable e) {
        if (isViewAttached()) {
            view.showFailed(e.getMessage());
        }
    }
}
