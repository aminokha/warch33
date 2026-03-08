package com.example.myapplication2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import java.util.Calendar;

public class NotificationHelper {
    private static final String TAG = "NotificationHelper";

    private static final String CHANNEL_ID = "smart_notification_channel";
    private static final String PREF_NAME = "NotificationPrefs";
    private static final String KEY_ENABLED = "notification_enabled";
    private static final String KEY_GENERAL_HOUR = "general_hour";
    private static final String KEY_GENERAL_MINUTE = "general_minute";
    private static final String KEY_ADVANCED_ENABLED = "advanced_enabled";

    // Keys for daily times: "day_hour_X" and "day_minute_X" where X is 1 (Sun) to 7
    // (Sat)
    // Calendar.SUNDAY = 1, ..., Calendar.SATURDAY = 7

    public static void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "تنبيهات التدريب",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_homat)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());
    }

    public static void scheduleNotifications(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean enabled = prefs.getBoolean(KEY_ENABLED, false);

        cancelAllNotifications(context);

        if (!enabled)
            return;

        boolean advanced = prefs.getBoolean(KEY_ADVANCED_ENABLED, false);

        if (advanced) {
            // Schedule for each day
            for (int day = 1; day <= 7; day++) {
                int hour = prefs.getInt("day_hour_" + day, 8);
                int minute = prefs.getInt("day_minute_" + day, 0);
                scheduleDaily(context, day, hour, minute);
            }
        } else {
            // Schedule daily general
            int hour = prefs.getInt(KEY_GENERAL_HOUR, 8);
            int minute = prefs.getInt(KEY_GENERAL_MINUTE, 0);
            scheduleDaily(context, -1, hour, minute); // -1 means every day
        }
    }

    private static void scheduleDaily(Context context, int dayOfWeek, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null)
            return;

        Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = (dayOfWeek == -1) ? 100 : 200 + dayOfWeek;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (dayOfWeek != -1) {
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        }

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            if (dayOfWeek == -1) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            } else {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
            } else {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Log.d(TAG, "Scheduled alarm for: " + calendar.getTime().toString() + " (RequestCode: " + requestCode + ")");
    }

    public static void cancelAllNotifications(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);

        // Cancel general
        PendingIntent pi1 = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pi1);

        // Cancel all daily
        for (int day = 1; day <= 7; day++) {
            PendingIntent pid = PendingIntent.getBroadcast(context, 200 + day, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.cancel(pid);
        }
    }

    public static boolean isEnabled(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_ENABLED, false);
    }

    public static void setEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_ENABLED, enabled).apply();
    }

    public static int getGeneralHour(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(KEY_GENERAL_HOUR, 8);
    }

    public static int getGeneralMinute(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(KEY_GENERAL_MINUTE, 0);
    }

    public static void setGeneralTime(Context context, int hour, int minute) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                .putInt(KEY_GENERAL_HOUR, hour)
                .putInt(KEY_GENERAL_MINUTE, minute)
                .apply();
    }

    public static boolean isAdvancedEnabled(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_ADVANCED_ENABLED, false);
    }

    public static void setAdvancedEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_ADVANCED_ENABLED, enabled)
                .apply();
    }

    public static void setDayTime(Context context, int dayOfWeek, int hour, int minute) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                .putInt("day_hour_" + dayOfWeek, hour)
                .putInt("day_minute_" + dayOfWeek, minute)
                .apply();
    }

    public static int getDayHour(Context context, int dayOfWeek) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt("day_hour_" + dayOfWeek, 8);
    }

    public static int getDayMinute(Context context, int dayOfWeek) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt("day_minute_" + dayOfWeek, 0);
    }
}
