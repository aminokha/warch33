package com.example.myapplication2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.StringCharacterIterator;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    VideoView videoView;
    FrameLayout videoLayout;
    SeekBar seekBar;
    TextView repeat;
    TextView harf;
    MediaPlayer mediaPlayer;
    RadioButton radioButtonFatha;
    RadioButton radioButtonThama;
    RadioButton radioButtonKasra;
    RadioButton radioButtonSokoun;
    ImageButton playButton;
    StringCharacterIterator stringCharacterIterator;
    private MediaController mediaController;


    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        stringCharacterIterator = new StringCharacterIterator("ءبتثجحخدذرزسشصضطظعغفقكلمنهوي");
        videoView = findViewById(R.id.videoView);
        videoLayout = findViewById(R.id.videoLayout);
        repeat = findViewById(R.id.repeat);
        harf = findViewById(R.id.harf);
        playButton = findViewById(R.id.playButton);
        radioButtonFatha = findViewById(R.id.radioButtonFatha);
        radioButtonThama = findViewById(R.id.radioButtonThama);
        radioButtonKasra = findViewById(R.id.radioButtonKasra);
        radioButtonSokoun = findViewById(R.id.radioButtonSokoun);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.hamza_a);
        mediaController = new MediaController(this);

        videoView.setOnCompletionListener(mediaPlayer1 -> {
            play(mediaPlayer1);
        });
        playButton.setTag("paused");


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

        mediaController.setAnchorView(videoLayout);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);

    }

    private void play(MediaPlayer mp) {
        int progress = seekBar.getProgress();
        if (progress > 0) {
            seekBar.setProgress(--progress);
            playButton.setImageResource(R.drawable.pause_icon);
            playButton.setTag("playing");
            checkWhatSound();
            videoView.start();

            seekBar.setEnabled(false);
            radioButtonFatha.setEnabled(false);
            radioButtonThama.setEnabled(false);
            radioButtonKasra.setEnabled(false);
            radioButtonSokoun.setEnabled(false);
        } else {
            pause();
        }
    }

    private void checkWhatSound() {
        String harfString = harf.getText().toString();
        switch (harfString) {
            case "ء": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_a);
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
        }

    }

    public void play_or_pause(View view) {

        if (playButton.getTag().toString().equalsIgnoreCase("paused")) {
            play(mediaPlayer);
        } else {
            pause();
        }

    }

    private void pause() {
        videoView.pause();
        playButton.setTag("paused");
        playButton.setImageResource(R.drawable.play_icon);
        seekBar.setEnabled(true);
        radioButtonFatha.setEnabled(true);
        radioButtonThama.setEnabled(true);
        radioButtonKasra.setEnabled(true);
        radioButtonSokoun.setEnabled(true);
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
