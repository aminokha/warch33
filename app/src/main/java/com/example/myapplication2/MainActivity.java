package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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
