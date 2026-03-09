package com.example.myapplication2;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText editGoalHorof;
    private TextInputEditText editGoalSouar;
    private TextInputLayout layoutGoalHorof;
    private TextInputLayout layoutGoalSouar;
    private Button btnSave;
    private StatsManager statsManager;
    private static final long BACK_PRESS_INTERVAL_MS = 2000;
    private long lastBackPressedTime;

    // Notification Views
    private SwitchCompat switchNotification;
    private TextView tvGeneralTime;
    private TextView tvAdvancedOptions;
    private LinearLayout layoutAdvanced;
    private View[] dayViews = new View[8]; // index 1-7 for Calendar days

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.settings_layout);

        editGoalHorof = findViewById(R.id.editGoalHorof);
        editGoalSouar = findViewById(R.id.editGoalSouar);
        layoutGoalHorof = findViewById(R.id.layoutGoalHorof);
        layoutGoalSouar = findViewById(R.id.layoutGoalSouar);
        btnSave = findViewById(R.id.btnSave);
        statsManager = StatsManager.getInstance(this);

        // Load current goals
        editGoalHorof.setText(String.valueOf(statsManager.getGoalHorof()));
        editGoalSouar.setText(String.valueOf(statsManager.getGoalSouar()));

        initNotificationSettings();

        btnSave.setOnClickListener(v -> saveSettings());

        com.google.android.material.bottomnavigation.BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_settings);
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_horof) {
                startActivity(new Intent(getApplicationContext(), HorofActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.nav_souar) {
                startActivity(new Intent(getApplicationContext(), SouarActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.nav_stats) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.nav_settings) {
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastBackPressedTime <= BACK_PRESS_INTERVAL_MS) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = now;
            Toast.makeText(this, "اضغط مرة أخرى للخروج", Toast.LENGTH_SHORT).show();
        }
    }

    private void initNotificationSettings() {
        switchNotification = findViewById(R.id.switchNotification);
        tvGeneralTime = findViewById(R.id.tvGeneralTime);
        tvAdvancedOptions = findViewById(R.id.tvAdvancedOptions);
        layoutAdvanced = findViewById(R.id.layoutAdvanced);

        // Map day views
        dayViews[Calendar.SATURDAY] = findViewById(R.id.day_sat);
        dayViews[Calendar.SUNDAY] = findViewById(R.id.day_sun);
        dayViews[Calendar.MONDAY] = findViewById(R.id.day_mon);
        dayViews[Calendar.TUESDAY] = findViewById(R.id.day_tue);
        dayViews[Calendar.WEDNESDAY] = findViewById(R.id.day_wed);
        dayViews[Calendar.THURSDAY] = findViewById(R.id.day_thu);
        dayViews[Calendar.FRIDAY] = findViewById(R.id.day_fri);

        String[] dayNames = { "", "الأحد", "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت" };

        for (int i = 1; i <= 7; i++) {
            final int dayIndex = i;
            TextView tvName = dayViews[i].findViewById(R.id.tvDayName);
            TextView tvTime = dayViews[i].findViewById(R.id.tvDayTime);
            tvName.setText(dayNames[i]);

            int h = NotificationHelper.getDayHour(this, i);
            int m = NotificationHelper.getDayMinute(this, i);
            tvTime.setText(formatTime(h, m));

            tvTime.setOnClickListener(v -> showTimePicker(h, m, (hour, minute) -> {
                NotificationHelper.setDayTime(this, dayIndex, hour, minute);
                tvTime.setText(formatTime(hour, minute));
            }));
        }

        // General Time
        int gh = NotificationHelper.getGeneralHour(this);
        int gm = NotificationHelper.getGeneralMinute(this);
        tvGeneralTime.setText(formatTime(gh, gm));
        tvGeneralTime.setOnClickListener(v -> showTimePicker(gh, gm, (hour, minute) -> {
            NotificationHelper.setGeneralTime(this, hour, minute);
            tvGeneralTime.setText(formatTime(hour, minute));
        }));

        // Initial states
        switchNotification.setChecked(NotificationHelper.isEnabled(this));

        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkNotificationPermission();
            }
        });

        if (NotificationHelper.isAdvancedEnabled(this)) {
            layoutAdvanced.setVisibility(View.VISIBLE);
        }

        tvAdvancedOptions.setOnClickListener(v -> {
            if (layoutAdvanced.getVisibility() == View.VISIBLE) {
                layoutAdvanced.setVisibility(View.GONE);
                NotificationHelper.setAdvancedEnabled(this, false);
            } else {
                layoutAdvanced.setVisibility(View.VISIBLE);
                NotificationHelper.setAdvancedEnabled(this, true);
            }
        });
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.POST_NOTIFICATIONS }, 101);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            android.app.AlarmManager alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(this, "يرجى تفعيل خيار المنبهات الدقيقة لضمان وصول التنبيه في وقته", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "تم تفعيل إذن التنبيهات", Toast.LENGTH_SHORT).show();
            } else {
                switchNotification.setChecked(false);
                Toast.makeText(this, "يجب الموافقة على إذن التنبيهات لتفعيل هذه الخاصية", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showTimePicker(int hour, int minute, TimePickerListener listener) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, h, m) -> {
            listener.onTimeSelected(h, m);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    interface TimePickerListener {
        void onTimeSelected(int hour, int minute);
    }

    private void saveSettings() {
        String horofStr = editGoalHorof.getText().toString();
        String souarStr = editGoalSouar.getText().toString();

        layoutGoalHorof.setError(null);
        layoutGoalSouar.setError(null);

        if (horofStr.isEmpty() || souarStr.isEmpty()) {
            Toast.makeText(this, "يرجى إدخال جميع القيم", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int horofGoal = Integer.parseInt(NumberUtils.normalize(horofStr));
            int souarGoal = Integer.parseInt(NumberUtils.normalize(souarStr));
            boolean hasError = false;

            if (horofGoal <= 0) {
                layoutGoalHorof.setError("يجب أن يكون الهدف أكبر من 0");
                hasError = true;
            } else if (horofGoal > 10000) {
                layoutGoalHorof.setError("أقصى عدد للهدف اليومي هو 10,000.");
                hasError = true;
            }

            if (souarGoal <= 0) {
                layoutGoalSouar.setError("يجب أن يكون الهدف أكبر من 0");
                hasError = true;
            } else if (souarGoal > 10000) {
                layoutGoalSouar.setError("أقصى عدد للهدف اليومي هو 10 000.");
                hasError = true;
            }

            if (hasError)
                return;

            statsManager.setGoalHorof(horofGoal);
            statsManager.setGoalSouar(souarGoal);

            // Save notification status and schedule
            NotificationHelper.setEnabled(this, switchNotification.isChecked());
            NotificationHelper.scheduleNotifications(this);

            Toast.makeText(this, "تم حفظ الإعدادات بنجاح", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "يرجى إدخال أرقام صحيحة", Toast.LENGTH_SHORT).show();
        }
    }
}
