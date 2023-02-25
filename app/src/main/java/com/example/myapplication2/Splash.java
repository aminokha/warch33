package com.example.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication2.MainActivity.ANDROID_ID;
import static com.example.myapplication2.MainActivity.active_Key;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 3000; // After completion of 2000 ms, the next activity will get started.
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This method is used so that your splash activity can cover the entire screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(() -> {
            dbHelper = new DBHelper(this);
            ANDROID_ID = getAndroidId();
            Cursor cursor = dbHelper.getData(String.valueOf(ANDROID_ID));
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String s_id = cursor.getString(0);
                    String s_key = cursor.getString(1);
                    active_Key = getAndroidId().hashCode();
                    if (s_key.equals(String.valueOf(active_Key))) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i); // invoke the SecondActivity.
                }
            }
            finish(); // the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT);


    }
    String getAndroidId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
}