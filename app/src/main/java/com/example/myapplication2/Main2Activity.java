package com.example.myapplication2;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    SeekBar seekBar;
    TextView repeat;
    TextView harf;
    MediaPlayer mediaPlayer;


    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        repeat = findViewById(R.id.repeat);
        harf = findViewById(R.id.harf);
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.sound1);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repeat.setText(String.valueOf(progress));
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            int progress = seekBar.getProgress();
            if (progress > 0) {
                seekBar.setProgress(progress - 1);
                mp.start();
            } else {
                seekBar.setEnabled(true);
            }

        });
    }

    private void checkWhatSound() {
        Character ch = harf.getText().charAt(0);
        System.out.println((int) ch);//1569


    }

    public void play(View view) {
        checkWhatSound();
        seekBar.setEnabled(false);
        mediaPlayer.start();
    }


}
