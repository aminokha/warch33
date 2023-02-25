package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static String ANDROID_ID;
    static int active_Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);



    }

    public void horof(View view) {
        Intent intent = new Intent(this, HorofActivity.class);
        startActivity(intent);
    }

    public void goToSouar(View view) {
        Intent intent = new Intent(this, SouarActivity.class);
        startActivity(intent);
    }

}
