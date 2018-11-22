package com.mohamed14riad.weather.selecting;

import android.content.Context;

public class SelectDialogPresenterImpl implements SelectDialogPresenter {
    private SelectDialogView view;
    private SelectDialogInteractor interactor;

    public SelectDialogPresenterImpl(Context context) {
        interactor = new SelectDialogInteractorImpl(context);
    }

    private boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void setView(SelectDialogView view) {
        this.view = view;
    }

    @Override
    public void setLastSavedOption() {
        if (isViewAttached()) {
            String city = interactor.getSelectedCity();

            switch (city) {
                case SelectType.CAIRO:
                    view.setCairoChecked();
                    break;
                case SelectType.DUBAI:
                    view.setDubaiChecked();
                    break;
                case SelectType.PARIS:
                    view.setParisChecked();
                    break;
                case SelectType.LONDON:
                    view.setLondonChecked();
                    break;
                case SelectType.CANADA:
                    view.setCanadaChecked();
                    break;
                default:
                    view.setCairoChecked();
                    break;
            }
        }
    }

    @Override
    public void onCairoSelected() {
        if (isViewAttached()) {
            interactor.setSelectedCity(SelectType.CAIRO);
            view.dismissDialog();
        }
    }

    @Override
    public void onDubaiSelected() {
        if (isViewAttached()) {
            interactor.setSelectedCity(SelectType.DUBAI);
            view.dismissDialog();
        }
    }

    @Override
    public void onParisSelected() {
        if (isViewAttached()) {
            interactor.setSelectedCity(SelectType.PARIS);
            view.dismissDialog();
        }
    }

    @Override
    public void onLondonSelected() {
        if (isViewAttached()) {
            interactor.setSelectedCity(SelectType.LONDON);
            view.dismissDialog();
        }
    }

    @Override
    public void onCanadaSelected() {
        if (isViewAttached()) {
            interactor.setSelectedCity(SelectType.CANADA);
            view.dismissDialog();
        }
    }

    @Override
    public void destroy() {
        view = null;
    }
}
