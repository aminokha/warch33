package com.example.myapplication2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(DailyStats stats);

    @Query("SELECT * FROM daily_stats WHERE date = :date")
    DailyStats getStatsForDate(String date);

    @Query("SELECT * FROM daily_stats ORDER BY date DESC")
    List<DailyStats> getAllStats();

    @Query("SELECT * FROM daily_stats WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    List<DailyStats> getStatsInRange(String startDate, String endDate);
}
