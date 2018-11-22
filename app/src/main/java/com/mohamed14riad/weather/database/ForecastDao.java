package com.mohamed14riad.weather.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecastList(List<ForecastEntry> entries);

    @Query("DELETE FROM forecast WHERE city = :city")
    void deleteForecastList(String city);

    @Query("SELECT * FROM forecast WHERE city = :city ORDER BY id")
    List<ForecastEntry> loadForecastList(String city);
}
