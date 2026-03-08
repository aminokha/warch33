package com.example.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.myapplication2.data.AppDatabase;
import com.example.myapplication2.data.DailyStats;
import com.example.myapplication2.data.StatsDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class StatsManager {

    private static final String PREF_NAME = "DailyStats";
    private static final String KEY_LAST_DATE = "last_date";
    private static final String KEY_GOAL_HOROF = "goal_horof";
    private static final String KEY_GOAL_SOUAR = "goal_souar";
    private static final String KEY_MIGRATED = "migrated_to_db";

    public static final int DEFAULT_GOAL_HOROF = 200;
    public static final int DEFAULT_GOAL_SOUAR = 20;

    private static StatsManager instance;
    private SharedPreferences sharedPreferences;
    private StatsDao statsDao;

    private StatsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        statsDao = AppDatabase.getInstance(context).statsDao();
        migrateIfNecessary();
    }

    public static synchronized StatsManager getInstance(Context context) {
        if (instance == null) {
            instance = new StatsManager(context.getApplicationContext());
        }
        return instance;
    }

    private void migrateIfNecessary() {
        if (!sharedPreferences.getBoolean(KEY_MIGRATED, false)) {
            int currentGoalH = getGoalHorof();
            int currentGoalS = getGoalSouar();

            Map<String, ?> allEntries = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("horof_history_")) {
                    String date = key.replace("horof_history_", "");
                    int hCount = (Integer) entry.getValue();
                    int sCount = sharedPreferences.getInt("souar_history_" + date, 0);
                    // Use the current goal at the time of migration for historical data
                    statsDao.insertOrReplace(new DailyStats(date, hCount, sCount, currentGoalH, currentGoalS));
                }
            }

            String today = getTodayDate();
            int h = sharedPreferences.getInt("horof_count", 0);
            int s = sharedPreferences.getInt("souar_count", 0);
            if (h > 0 || s > 0) {
                statsDao.insertOrReplace(new DailyStats(today, h, s, currentGoalH, currentGoalS));
            }

            sharedPreferences.edit().putBoolean(KEY_MIGRATED, true).apply();
        }
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    private DailyStats getTodayStats() {
        String today = getTodayDate();
        DailyStats stats = statsDao.getStatsForDate(today);
        int currentGoalH = getGoalHorof();
        int currentGoalS = getGoalSouar();

        if (stats == null) {
            stats = new DailyStats(today, 0, 0, currentGoalH, currentGoalS);
            statsDao.insertOrReplace(stats);
        } else {
            // Update today's target if it was changed in settings
            if (stats.getTargetHorof() != currentGoalH || stats.getTargetSouar() != currentGoalS) {
                stats.setTargetHorof(currentGoalH);
                stats.setTargetSouar(currentGoalS);
                statsDao.insertOrReplace(stats);
            }
        }
        return stats;
    }

    public void incrementHorofCount() {
        DailyStats stats = getTodayStats();
        stats.setHorofCount(stats.getHorofCount() + 1);
        statsDao.insertOrReplace(stats);
    }

    public void incrementSouarCount() {
        DailyStats stats = getTodayStats();
        stats.setSouarCount(stats.getSouarCount() + 1);
        statsDao.insertOrReplace(stats);
    }

    public int getHorofCount() {
        return getTodayStats().getHorofCount();
    }

    public int getSouarCount() {
        return getTodayStats().getSouarCount();
    }

    public int getGoalHorof() {
        return sharedPreferences.getInt(KEY_GOAL_HOROF, DEFAULT_GOAL_HOROF);
    }

    public void setGoalHorof(int goal) {
        sharedPreferences.edit().putInt(KEY_GOAL_HOROF, goal).apply();
        // Today's target will be updated on next getTodayStats() call
    }

    public int getGoalSouar() {
        return sharedPreferences.getInt(KEY_GOAL_SOUAR, DEFAULT_GOAL_SOUAR);
    }

    public void setGoalSouar(int goal) {
        sharedPreferences.edit().putInt(KEY_GOAL_SOUAR, goal).apply();
        // Today's target will be updated on next getTodayStats() call
    }

    public int[] getWeeklyActivityStartingSaturday() {
        int[] result = new int[7];
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int currentDayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysSinceSaturday = (currentDayOfWeek == java.util.Calendar.SATURDAY) ? 0 : currentDayOfWeek;
        cal.add(java.util.Calendar.DAY_OF_YEAR, -daysSinceSaturday);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        for (int i = 0; i < 7; i++) {
            String dateStr = sdf.format(cal.getTime());
            DailyStats stats = statsDao.getStatsForDate(dateStr);
            if (stats != null) {
                result[i] = stats.getHorofCount() + stats.getSouarCount();
            } else {
                result[i] = 0;
            }
            cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public int[][] getWeeklyActivityDetailedStartingSaturday() {
        int[][] result = new int[7][2];
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int currentDayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysSinceSaturday = (currentDayOfWeek == java.util.Calendar.SATURDAY) ? 0 : currentDayOfWeek;
        cal.add(java.util.Calendar.DAY_OF_YEAR, -daysSinceSaturday);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        for (int i = 0; i < 7; i++) {
            String dateStr = sdf.format(cal.getTime());
            DailyStats stats = statsDao.getStatsForDate(dateStr);
            if (stats != null) {
                result[i][0] = stats.getHorofCount();
                result[i][1] = stats.getSouarCount();
            } else {
                result[i][0] = 0;
                result[i][1] = 0;
            }
            cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public boolean[] getPast7DaysActiveStatus() {
        boolean[] result = new boolean[7];
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        for (int i = 6; i >= 0; i--) {
            String dateStr = sdf.format(cal.getTime());
            DailyStats stats = statsDao.getStatsForDate(dateStr);
            if (stats != null) {
                // Use the frozen target for that specific day
                result[i] = (stats.getHorofCount() >= stats.getTargetHorof() ||
                        stats.getSouarCount() >= stats.getTargetSouar());
            } else {
                result[i] = false;
            }
            cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
        }
        return result;
    }

    public int getCurrentStreak() {
        int streak = 0;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        DailyStats todayStats = statsDao.getStatsForDate(sdf.format(cal.getTime()));
        boolean todayActive = (todayStats != null &&
                (todayStats.getHorofCount() >= todayStats.getTargetHorof() ||
                        todayStats.getSouarCount() >= todayStats.getTargetSouar()));

        cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
        DailyStats yesterdayStats = statsDao.getStatsForDate(sdf.format(cal.getTime()));
        boolean yesterdayActive = (yesterdayStats != null &&
                (yesterdayStats.getHorofCount() >= yesterdayStats.getTargetHorof() ||
                        yesterdayStats.getSouarCount() >= yesterdayStats.getTargetSouar()));

        if (!todayActive && !yesterdayActive)
            return 0;

        if (todayActive)
            streak++;

        // Count backwards from yesterday
        for (int i = 0; i < 365; i++) {
            DailyStats s = statsDao.getStatsForDate(sdf.format(cal.getTime()));
            if (s != null && (s.getHorofCount() >= s.getTargetHorof() ||
                    s.getSouarCount() >= s.getTargetSouar())) {
                streak++;
                cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
            } else {
                break;
            }
        }
        return streak;
    }
}
