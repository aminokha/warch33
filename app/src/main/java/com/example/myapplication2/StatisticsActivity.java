package com.example.myapplication2;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    private ProgressBar progressHorof;
    private TextView textHorof;
    private ProgressBar progressSouar;
    private TextView textSouar;
    private com.github.mikephil.charting.charts.BarChart barChart;
    private TextView textCurrentStreak;
    private android.widget.LinearLayout layoutStreakDays;
    private StatsManager statsManager;
    private static final long BACK_PRESS_INTERVAL_MS = 2000;
    private long lastBackPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.statistics_layout);

        progressHorof = findViewById(R.id.progressHorof);
        textHorof = findViewById(R.id.textHorof);
        progressSouar = findViewById(R.id.progressSouar);
        textSouar = findViewById(R.id.textSouar);
        barChart = findViewById(R.id.barChart);
        textCurrentStreak = findViewById(R.id.textCurrentStreak);
        layoutStreakDays = findViewById(R.id.layoutStreakDays);

        statsManager = StatsManager.getInstance(this);

        setupChart();

        com.google.android.material.bottomnavigation.BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_stats);
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
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastBackPressedTime <= BACK_PRESS_INTERVAL_MS) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = now;
            android.widget.Toast.makeText(this, "اضغط مرة أخرى للخروج", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    private void setupChart() {
        // Set custom renderer for top rounded corners
        barChart.setRenderer(
                new RoundedBarChartRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler(), 16f));

        barChart.setDescription(null);
        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setTextColor(android.graphics.Color.WHITE);
        barChart.getLegend().setHorizontalAlignment(
                com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        // Move Y Axis to Right
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(true);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setAxisMinimum(0f);
        barChart.getAxisRight().setTextColor(android.graphics.Color.WHITE);

        com.github.mikephil.charting.components.XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(android.graphics.Color.WHITE);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        // Days array in reversed order for RTL display (Fri at 0, Sat at 6)
        final String[] days = { "الجمعة", "الخميس", "الأربعاء", "الثلاثاء", "الاثنين", "الأحد", "السبت" };
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < days.length) {
                    return days[index];
                }
                return "";
            }
        });

        barChart.animateY(1500);
    }

    private void updateUI() {
        int horofCount = statsManager.getHorofCount();
        int goalHorof = statsManager.getGoalHorof();

        progressHorof.setMax(goalHorof);

        ObjectAnimator animHorof = ObjectAnimator.ofInt(progressHorof, "progress", 0, horofCount);
        animHorof.setDuration(1500);
        animHorof.setInterpolator(new android.view.animation.DecelerateInterpolator());
        animHorof.start();

        textHorof.setText(String.format(Locale.US, "%d / %d", horofCount, goalHorof));

        int souarCount = statsManager.getSouarCount();
        int goalSouar = statsManager.getGoalSouar();

        progressSouar.setMax(goalSouar);

        ObjectAnimator animSouar = ObjectAnimator.ofInt(progressSouar, "progress", 0, souarCount);
        animSouar.setDuration(1500);
        animSouar.setInterpolator(new android.view.animation.DecelerateInterpolator());
        animSouar.start();

        textSouar.setText(String.format(Locale.US, "%d / %d", souarCount, goalSouar));

        int[][] detailedActivities = statsManager.getWeeklyActivityDetailedStartingSaturday();
        java.util.ArrayList<com.github.mikephil.charting.data.BarEntry> entriesHorof = new java.util.ArrayList<>();
        java.util.ArrayList<com.github.mikephil.charting.data.BarEntry> entriesSouar = new java.util.ArrayList<>();
        // Sat(0) at X=6, Sun(1) at X=5, ..., Fri(6) at X=0
        for (int i = 0; i < 7; i++) {
            entriesHorof.add(new com.github.mikephil.charting.data.BarEntry(6 - i, (float) detailedActivities[i][0]));
            entriesSouar.add(new com.github.mikephil.charting.data.BarEntry(6 - i, (float) detailedActivities[i][1]));
        }

        com.github.mikephil.charting.data.BarDataSet setHorof = new com.github.mikephil.charting.data.BarDataSet(
                entriesHorof, "الحروف");
        setHorof.setColor(androidx.core.content.ContextCompat.getColor(this, R.color.primary));
        setHorof.setValueTextColor(android.graphics.Color.WHITE);
        setHorof.setValueTextSize(10f);

        com.github.mikephil.charting.data.BarDataSet setSouar = new com.github.mikephil.charting.data.BarDataSet(
                entriesSouar, "السور");
        setSouar.setColor(androidx.core.content.ContextCompat.getColor(this, R.color.secondary));
        setSouar.setValueTextColor(android.graphics.Color.WHITE);
        setSouar.setValueTextSize(10f);

        com.github.mikephil.charting.formatter.ValueFormatter vf = new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0)
                    return "";
                return String.valueOf((int) value);
            }
        };
        setHorof.setValueFormatter(vf);
        setSouar.setValueFormatter(vf);

        com.github.mikephil.charting.data.BarData barData = new com.github.mikephil.charting.data.BarData(setHorof,
                setSouar);
        float groupSpace = 0.40f;
        float barSpace = 0.05f;
        float barWidth = 0.25f;
        barData.setBarWidth(barWidth);

        barChart.setData(barData);
        barChart.groupBars(0f, groupSpace, barSpace);
        barChart.getXAxis().setAxisMinimum(0f);
        barChart.getXAxis().setAxisMaximum(barData.getGroupWidth(groupSpace, barSpace) * 7);
        barChart.invalidate();

        updateStreakUI();
    }

    private void updateStreakUI() {
        int streak = statsManager.getCurrentStreak();
        textCurrentStreak.setText("🔥 " + streak + " أيام متتالية");

        layoutStreakDays.removeAllViews();
        boolean[] past7Days = statsManager.getPast7DaysActiveStatus();

        for (int i = 0; i < 7; i++) {
            android.widget.LinearLayout dayLayout = new android.widget.LinearLayout(this);
            dayLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
            dayLayout.setGravity(android.view.Gravity.CENTER);

            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
                    0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            dayLayout.setLayoutParams(layoutParams);

            View circle = new View(this);
            int size = (int) (24 * getResources().getDisplayMetrics().density);
            android.widget.LinearLayout.LayoutParams circleParams = new android.widget.LinearLayout.LayoutParams(size,
                    size);
            circleParams.bottomMargin = (int) (4 * getResources().getDisplayMetrics().density);
            circle.setLayoutParams(circleParams);

            android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
            shape.setShape(android.graphics.drawable.GradientDrawable.OVAL);
            if (past7Days[i]) {
                shape.setColor(android.graphics.Color.parseColor("#4CAF50")); // Green
            } else {
                shape.setColor(android.graphics.Color.parseColor("#E0E0E0")); // Gray
            }
            circle.setBackground(shape);

            TextView dayLabel = new TextView(this);
            if (i == 6) {
                dayLabel.setText("اليوم");
                dayLabel.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
            } else {
                dayLabel.setText("");
            }
            dayLabel.setTextSize(10);
            dayLabel.setGravity(android.view.Gravity.CENTER);

            dayLayout.addView(circle);
            dayLayout.addView(dayLabel);

            layoutStreakDays.addView(dayLayout);
        }
    }
}
