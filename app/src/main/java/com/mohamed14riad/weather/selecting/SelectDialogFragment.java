package com.mohamed14riad.weather.selecting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mohamed14riad.weather.R;
import com.mohamed14riad.weather.listing.MainPresenter;
import com.mohamed14riad.weather.utils.Constant;

import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SelectDialogFragment extends DialogFragment implements SelectDialogView, RadioGroup.OnCheckedChangeListener {
    private SelectDialogPresenter presenter;
    private static MainPresenter mainPresenter;

    private RadioGroup selectingGroup;
    private RadioButton cairo;
    private RadioButton dubai;
    private RadioButton paris;
    private RadioButton london;
    private RadioButton canada;

    private static final String API_KEY = Constant.API_KEY;

    public static SelectDialogFragment newInstance(MainPresenter mainPresenter) {
        SelectDialogFragment.mainPresenter = mainPresenter;
        return new SelectDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new SelectDialogPresenterImpl(getContext());
        presenter.setView(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.selecting_dialog, null);

        selectingGroup = (RadioGroup) dialogView.findViewById(R.id.selecting_group);
        cairo = (RadioButton) dialogView.findViewById(R.id.cairo);
        dubai = (RadioButton) dialogView.findViewById(R.id.dubai);
        paris = (RadioButton) dialogView.findViewById(R.id.paris);
        london = (RadioButton) dialogView.findViewById(R.id.london);
        canada = (RadioButton) dialogView.findViewById(R.id.canada);

        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.select_city);
        dialog.show();

        return dialog;
    }

    private void initViews() {
        if (API_KEY.isEmpty()) {
            cairo.setEnabled(false);
            dubai.setEnabled(false);
            paris.setEnabled(false);
            london.setEnabled(false);
            canada.setEnabled(false);
        } else {
            presenter.setLastSavedOption();

            selectingGroup.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.cairo:
                presenter.onCairoSelected();
                mainPresenter.showInfo();
                break;
            case R.id.dubai:
                presenter.onDubaiSelected();
                mainPresenter.showInfo();
                break;
            case R.id.paris:
                presenter.onParisSelected();
                mainPresenter.showInfo();
                break;
            case R.id.london:
                presenter.onLondonSelected();
                mainPresenter.showInfo();
                break;
            case R.id.canada:
                presenter.onCanadaSelected();
                mainPresenter.showInfo();
                break;
        }
    }

    @Override
    public void setCairoChecked() {
        cairo.setChecked(true);
    }

    @Override
    public void setDubaiChecked() {
        dubai.setChecked(true);
    }

    @Override
    public void setParisChecked() {
        paris.setChecked(true);
    }

    @Override
    public void setLondonChecked() {
        london.setChecked(true);
    }

    @Override
    public void setCanadaChecked() {
        canada.setChecked(true);
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
    }
}
