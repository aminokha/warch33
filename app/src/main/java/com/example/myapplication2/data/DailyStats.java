package com.example.myapplication2.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_stats")
public class DailyStats {
    @PrimaryKey
    @NonNull
    private String date; // yyyy-MM-dd
    private int horofCount;
    private int souarCount;
    private int targetHorof;
    private int targetSouar;

    public DailyStats(@NonNull String date, int horofCount, int souarCount, int targetHorof, int targetSouar) {
        this.date = date;
        this.horofCount = horofCount;
        this.souarCount = souarCount;
        this.targetHorof = targetHorof;
        this.targetSouar = targetSouar;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public int getHorofCount() {
        return horofCount;
    }

    public void setHorofCount(int horofCount) {
        this.horofCount = horofCount;
    }

    public int getSouarCount() {
        return souarCount;
    }

    public void setSouarCount(int souarCount) {
        this.souarCount = souarCount;
    }

    public int getTargetHorof() {
        return targetHorof;
    }

    public void setTargetHorof(int targetHorof) {
        this.targetHorof = targetHorof;
    }

    public int getTargetSouar() {
        return targetSouar;
    }

    public void setTargetSouar(int targetSouar) {
        this.targetSouar = targetSouar;
    }
}
