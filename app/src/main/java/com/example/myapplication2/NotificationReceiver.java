package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(context, "تذكير التدريب اليومي",
                "حان وقت التدريب اليومي\uD83D\uDD25\uD83D\uDD25. لا تدع يومك يمضي دون تدريب.");
        // Reschedule for next occurrence
        NotificationHelper.scheduleNotifications(context);
    }
}
