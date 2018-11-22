package com.mohamed14riad.weather.listing;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mohamed14riad.weather.R;
import com.mohamed14riad.weather.database.AppDatabase;
import com.mohamed14riad.weather.database.ForecastEntry;
import com.mohamed14riad.weather.pojo.Forecast;
import com.mohamed14riad.weather.pojo.MainInfo;
import com.mohamed14riad.weather.pojo.Weather;
import com.mohamed14riad.weather.pojo.Wind;
import com.mohamed14riad.weather.selecting.SelectDialogFragment;
import com.mohamed14riad.weather.selecting.SelectStore;
import com.mohamed14riad.weather.utils.AppExecutors;
import com.mohamed14riad.weather.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainFragment extends Fragment implements MainView {

    /* First You Should Insert Your API KEY In Constant Class */
    private static final String API_KEY = Constant.API_KEY;

    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;
    private Snackbar snackbar;

    private ForecastAdapter adapter;

    private MainPresenter presenter;

    private AppDatabase database;

    private List<ForecastEntry> entryList;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        presenter = new MainPresenterImpl(getContext());
        database = AppDatabase.getInstance(Objects.requireNonNull(getContext()).getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please Obtain Your API KEY First From openweathermap.org", Toast.LENGTH_LONG).show();
            return null;
        }

        adapter = new ForecastAdapter(getContext());

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        loadingIndicator = rootView.findViewById(R.id.loadingIndicator);

        entryList = new ArrayList<>();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_select:
                displaySortingOptions();
                return true;
            case R.id.item_contact_us:
                Intent mailToIntent = new Intent(Intent.ACTION_SEND);
                mailToIntent.setData(Uri.parse("mailto:"));
                mailToIntent.setType("text/plain");
                mailToIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
                startActivity(Intent.createChooser(mailToIntent, getString(R.string.send_mail)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySortingOptions() {
        SelectDialogFragment selectDialogFragment = SelectDialogFragment.newInstance(presenter);
        selectDialogFragment.show(Objects.requireNonNull(getFragmentManager()), "Selecting");
    }

    @Override
    public void showWeatherInfo(List<Forecast> list) {
        // Insert Into Database
        saveForOffline(list);
    }

    private void saveForOffline(List<Forecast> list) {
        SelectStore store = new SelectStore(Objects.requireNonNull(getContext()));

        entryList.clear();
        for (int i = 0; i < list.size(); i++) {
            Forecast forecast = list.get(i);
            MainInfo mainInfo = forecast.getMainInfo();
            List<Weather> weatherList = forecast.getWeather();
            Weather weather = weatherList.get(0);
            Wind wind = forecast.getWind();

            String city = store.getSelectedCity();
            String icon = Weather.getIconUrl(weather);
            String description = weather.getDescription();
            String temperature = String.valueOf(mainInfo.getTemp());
            String maxTemp = String.valueOf(mainInfo.getTempMax());
            String minTemp = String.valueOf(mainInfo.getTempMin());
            String pressure = String.valueOf(mainInfo.getPressure());
            String humidity = String.valueOf(mainInfo.getHumidity());
            String windSpeed = String.valueOf(wind.getSpeed());
            String windDegree = String.valueOf(wind.getDeg());
            String date = forecast.getDtTxt();

            ForecastEntry entry = new ForecastEntry(city, icon, description, temperature, maxTemp, minTemp, pressure, humidity, windSpeed, windDegree, date);

            entryList.add(entry);
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.forecastDao().deleteForecastList(store.getSelectedCity());
                database.forecastDao().insertForecastList(entryList);
                List<ForecastEntry> entries = database.forecastDao().loadForecastList(store.getSelectedCity());

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayWeatherInfo(entries);
                    }
                });
            }
        });
    }

    private void displayWeatherInfo(List<ForecastEntry> entries) {
        if (entries != null && !entries.isEmpty()) {
            entryList.clear();
            entryList.addAll(entries);

            adapter.setForecastList(entryList);

            loadingIndicator.setVisibility(View.GONE);

            if (snackbar != null && snackbar.isShown()) {
                snackbar.dismiss();
            }
        }

        if (entries != null && entries.isEmpty()) {
            loadingIndicator.setVisibility(View.GONE);

            if (isConnected()) {
                snackbar = Snackbar.make(recyclerView, "No weather data available now", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            } else {
                snackbar = Snackbar.make(recyclerView, "No weather data available now\nYou are offline!", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        }
    }

    @Override
    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailed(String errorMessage) {
        loadingIndicator.setVisibility(View.GONE);

        if (isConnected()) {
            snackbar = Snackbar.make(recyclerView, errorMessage, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            SelectStore store = new SelectStore(Objects.requireNonNull(getContext()));

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<ForecastEntry> entries = database.forecastDao().loadForecastList(store.getSelectedCity());

                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayWeatherInfo(entries);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getContext())
                .getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
