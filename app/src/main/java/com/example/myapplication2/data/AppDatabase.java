package com.example.myapplication2.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { DailyStats.class }, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract StatsDao statsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "warch_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // Allowing main thread for simple counters to keep logic
                                                      // synchronous
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
