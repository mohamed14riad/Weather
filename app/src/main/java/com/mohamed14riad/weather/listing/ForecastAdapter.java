package com.mohamed14riad.weather.listing;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mohamed14riad.weather.R;
import com.mohamed14riad.weather.database.ForecastEntry;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private Context context;
    private List<ForecastEntry> forecastList;

    public ForecastAdapter(Context context) {
        this.context = context;
        forecastList = new ArrayList<>();
    }

    public void setForecastList(List<ForecastEntry> list) {
        this.forecastList.clear();
        this.forecastList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastEntry entry = forecastList.get(position);

        String icon = entry.getIcon();
        String description = entry.getDescription();
        String temperature = String.valueOf(entry.getTemperature()).concat(" \u00b0" + "C");
        String maxTemp = context.getString(R.string.max).concat(" " + String.valueOf(entry.getMaxTemp())).concat(" \u00b0" + "C");
        String minTemp = context.getString(R.string.min).concat(" " + String.valueOf(entry.getMinTemp())).concat(" \u00b0" + "C");
        String pressure = context.getString(R.string.pressure).concat(" " + String.valueOf(entry.getPressure())).concat(" hPa");
        String humidity = context.getString(R.string.humidity).concat(" " + String.valueOf(entry.getHumidity())).concat(" %");
        String windSpeed = context.getString(R.string.wind_speed).concat(" " + String.valueOf(entry.getWindSpeed())).concat(" KMH");
        String windDegree = context.getString(R.string.wind_degree).concat(" " + String.valueOf(entry.getWindDegree()));
        String date_time = context.getString(R.string.date_time).concat(" " + entry.getDate());

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(context)
                    .asBitmap()
                    .load(icon)
                    .into(holder.icon);

            holder.description.setText(description);
            holder.temperature.setText(temperature);
            holder.maxTemp.setText(maxTemp);
            holder.minTemp.setText(minTemp);
            holder.pressure.setText(pressure);
            holder.humidity.setText(humidity);
            holder.windSpeed.setText(windSpeed);
            holder.windDegree.setText(windDegree);
            holder.date.setText(date_time);
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(icon)
                    .into(holder.icon);

            holder.description.setText(description);
            holder.temperature.setText(temperature);
            holder.maxTemp.setText(maxTemp);
            holder.minTemp.setText(minTemp);
            holder.date.setText(date_time);
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView description, temperature, maxTemp, minTemp,
                pressure, humidity, windSpeed, windDegree, date;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            description = itemView.findViewById(R.id.description);
            temperature = itemView.findViewById(R.id.temperature);
            maxTemp = itemView.findViewById(R.id.maxTemp);
            minTemp = itemView.findViewById(R.id.minTemp);
            pressure = itemView.findViewById(R.id.pressure);
            humidity = itemView.findViewById(R.id.humidity);
            windSpeed = itemView.findViewById(R.id.windSpeed);
            windDegree = itemView.findViewById(R.id.windDegree);
            date = itemView.findViewById(R.id.date);
        }
    }
}
