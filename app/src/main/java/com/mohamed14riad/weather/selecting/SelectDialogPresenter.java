package com.mohamed14riad.weather.selecting;

public interface SelectDialogPresenter {
    void setView(SelectDialogView view);

    void setLastSavedOption();

    void onCairoSelected();

    void onDubaiSelected();

    void onParisSelected();

    void onLondonSelected();

    void onCanadaSelected();

    void destroy();
}
