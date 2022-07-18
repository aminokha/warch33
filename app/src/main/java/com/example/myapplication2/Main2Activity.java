package com.example.myapplication2;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.StringCharacterIterator;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    SeekBar seekBar;
    TextView repeat;
    TextView harf;
    MediaPlayer mediaPlayer;
    RadioButton radioButtonFatha;
    RadioButton radioButtonThama;
    RadioButton radioButtonKasra;
    ImageButton playButton;
    StringCharacterIterator stringCharacterIterator;


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
        playButton = findViewById(R.id.playButton);
        radioButtonFatha = findViewById(R.id.radioButtonFatha);
        radioButtonThama = findViewById(R.id.radioButtonThama);
        radioButtonKasra = findViewById(R.id.radioButtonKasra);
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.hamza_fatha);
        seekBar = findViewById(R.id.seekBar);

        stringCharacterIterator = new StringCharacterIterator("ءبتثجحخدذرزسشصضطظعغفقكلمنهوي");


        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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

        mediaPlayer.setOnCompletionListener(this::repeat);

    }

    private void repeat(MediaPlayer mp) {
        int progress = seekBar.getProgress();
        if (progress > 0) {
            seekBar.setProgress(progress - 1);
            mp.start();
        } else {
            seekBar.setEnabled(true);
            playButton.setEnabled(true);
            radioButtonFatha.setEnabled(true);
            radioButtonThama.setEnabled(true);
            radioButtonKasra.setEnabled(true);
        }
    }

    private void checkWhatSound() {
//        Character ch = harf.getText().charAt(0);
//        System.out.println((int) ch);//1569
        String harfString = harf.getText().toString();
//        RadioButton radio_checked = (RadioButton) radioGroup.getiChildAt(radioGroup.getCheckedRadioButtonId());
        switch (harfString) {
            case "ء": {
                if (radioButtonFatha.isChecked()) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.hamza_fatha);
                } else if (radioButtonThama.isChecked()) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.hamza_thama);
                } else {
                    mediaPlayer = MediaPlayer.create(this, R.raw.hamza_thama);
                }
                break;
            }
            case "ب": {

            }
            case "ت": {

            }
            case "ث": {

            }

            default: {
            }
        }
        mediaPlayer.setOnCompletionListener(this::repeat);
    }

    public void play(View view) {
        checkWhatSound();
        seekBar.setEnabled(false);
        playButton.setEnabled(false);
        radioButtonFatha.setEnabled(false);
        radioButtonThama.setEnabled(false);
        radioButtonKasra.setEnabled(false);
        repeat(mediaPlayer);
    }


    public void nextHarf(View view) {
        if (stringCharacterIterator.getIndex() < stringCharacterIterator.getEndIndex() - 1) {
            char c = stringCharacterIterator.next();
            String ch = String.valueOf(c);
            harf.setText(ch);
        }
    }

    public void previousHarf(View view) {
        if (stringCharacterIterator.getIndex() > stringCharacterIterator.getBeginIndex()) {
            char c = stringCharacterIterator.previous();
            String ch = String.valueOf(c);
            harf.setText(ch);
        }
    }

}
