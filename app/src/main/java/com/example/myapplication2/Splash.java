package com.example.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication2.MainActivity.ANDROID_ID;
import static com.example.myapplication2.MainActivity.active_Key;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This method is used so that your splash activity can cover the entire screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(() -> {
            DBHelper dbHelper = new DBHelper(this);
            ANDROID_ID = getAndroidId();
            active_Key = ANDROID_ID.hashCode();
            
            Cursor cursor = null;
            try {
                cursor = dbHelper.getData(ANDROID_ID);
                if (cursor != null && cursor.moveToFirst()) {
                    String s_key = cursor.getString(1);
                    if (s_key.equals(String.valueOf(active_Key))) {
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        startActivity(new Intent(this, Login.class));
                    }
                } else {
                    startActivity(new Intent(this, Login.class));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private String getAndroidId() {
        return Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
    }
}