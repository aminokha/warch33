package com.example.myapplication2;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioButton choice1;
    RadioButton choice2;
    StringCharacterIterator stringCharacterIterator;
    private MediaController mediaController;
    private RadioGroup radioGroup1;


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
        radioGroup1 = findViewById(R.id.radio_group1);
        radioButtonFatha = findViewById(R.id.radioButtonFatha);
        radioButtonThama = findViewById(R.id.radioButtonThama);
        radioButtonKasra = findViewById(R.id.radioButtonKasra);
        radioButtonSokoun = findViewById(R.id.radioButtonSokoun);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.hamza_fatha);
        mediaController = new MediaController(this);

        videoView.setOnCompletionListener(mediaPlayer1 -> {
            play(mediaPlayer1);
        });

        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setOnVideoSizeChangedListener((mediaPlayer1, width, height) -> {
            mediaController = new MediaController(Main2Activity.this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mediaController.setTransitionAlpha(0.5f);
            } else {
                mediaController.setAlpha(0.5f);
            }
        });
    });
        mediaController.setVisibility(View.VISIBLE);

    play(mediaPlayer);
        mediaPlayer.pause();

        seekBar.setOnSeekBarChangeListener(new

    OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch (SeekBar seekBar){

        }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){

        }

        @Override
        public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
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
            checkWhatSound();
            videoView.start();

            seekBar.setEnabled(false);
//            radioButtonFatha.setEnabled(false);
//            radioButtonThama.setEnabled(false);
//            radioButtonKasra.setEnabled(false);
//            radioButtonSokoun.setEnabled(false);
        } else {
            pause();
        }
    }

    private void checkWhatSound() {
        String harfString = harf.getText().toString();
        switch (harfString) {
            case "ء": {
                if (choice2.isChecked()) {
                    radioButtonSokoun.setEnabled(false);
                    if (radioButtonSokoun.isChecked()) {
                        radioButtonKasra.setChecked(true);
                    }
                    if (radioButtonFatha.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_mos_fatha);
                    } else if (radioButtonThama.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_mos_dhama);
                    } else if (radioButtonKasra.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_mos_kasra);
                    }
                } else {
                    radioButtonSokoun.setEnabled(true);
                    if (radioButtonFatha.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_fatha);
                    } else if (radioButtonThama.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_dhama);
                    } else if (radioButtonKasra.isChecked()) {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_kasra);
                    } else {
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hamza_sokoun);
                    }
                }
                break;
            }
            case "ب": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ba_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ba_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ba_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ba_sokoun);
                }
                break;
            }
            case "ت": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ta_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ta_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ta_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ta_sokoun);
                }
                break;

            }
            case "ث": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tha_sokoun);
                }
                break;
            }
            case "ج": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dja_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dja_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dja_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dja_sokoun);
                }
                break;
            }
            case "ح": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.h7a_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.h7a_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.h7a_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.h7a_sokoun);
                }
                break;
            }
            case "خ": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kha_sokoun);
                }
                break;
            }
            case "د": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.da_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.da_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.da_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.da_sokoun);
                }
                break;
            }
            case "ذ": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dha_sokoun);
                }
                break;
            }
            case "ر": {
                if (radioButtonFatha.isChecked()) {
                    if (choice1.isChecked()) { // راء مفتوحة مفخمة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_fathaaa);
                    } else { // راء مفتوحة مرققة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_fatha);
                    }
                } else if (radioButtonThama.isChecked()) {
                    if (choice1.isChecked()) { // راء مضمومة مفخمة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_dhamaaa2);
                    } else { // راء مضمومة مرققة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_dhama);
                    }
                } else if (radioButtonKasra.isChecked()) { //راء مكسورة
                    choice2.setChecked(true);
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_kasra);
                } else {
                    if (choice1.isChecked()) { // راء ساكنة مفخمة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_sokounnn);
                    } else { // راء ساكنة مرققة
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ra_sokoun);
                    }
                }
                break;
            }
            case "ز": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.za_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.za_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.za_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.za_sokoun);
                }
                break;
            }
            case "س": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sa_sokoun);
                }
                break;
            }
            case "ش": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sha_sokoun);
                }
                break;
            }
            case "ص": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.saa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.saa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.saa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.saa_sokoun);
                }
                break;
            }
            case "ض": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.daa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.daa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.daa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.daa_sokoun);
                }
                break;
            }
            case "ط": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.taa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.taa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.taa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.taa_sokoun);
                }
                break;
            }
            case "ظ": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dhaa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dhaa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dhaa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dhaa_sokoun);
                }
                break;
            }
            case "ع": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aa_sokoun);
                }
                break;
            }
            case "غ": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.gha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.gha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.gha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.gha_sokoun);
                }
                break;
            }
            case "ف": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fa_sokoun);
                }
                break;
            }
            case "ق": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kaa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kaa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kaa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kaa_sokoun);
                }
                break;
            }
            case "ك": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ka_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ka_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ka_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ka_sokoun);
                }
                break;
            }
            case "ل": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.la_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.la_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.la_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.la_sokoun);
                }
                break;
            }
            case "م": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ma_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ma_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ma_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ma_sokoun);
                }
                break;
            }
            case "ن": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.na_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.na_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.na_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.na_sokoun);
                }
                break;
            }
            case "ه": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ha_fatha2);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ha_dhama2);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ha_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ha_sokoun);
                }
                break;
            }
            case "و": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.wa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.wa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.wa_kasra);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.wa_sokoun);
                }
                break;
            }
            case "ي": {
                if (radioButtonFatha.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ya_fatha);
                } else if (radioButtonThama.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ya_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ya_kasra3);
                } else {
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ya_sokoun);
                }
                break;
            }

        }

    }

    public void play_or_pause(View view) {


    }

    private void pause() {
        videoView.pause();

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
            if (ch.equals("ء")) {
                choice1.setText("همزة محققة");
                choice2.setText("همزة مسهلة");
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else if (ch.equals("ر") || ch.equals("ل")) {
                choice1.setText("مفخم");
                choice2.setText("مرقق");
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            }
        }
    }

    public void previousHarf(View view) {
        if (stringCharacterIterator.getIndex() > stringCharacterIterator.getBeginIndex()) {
            char c = stringCharacterIterator.previous();
            String ch = String.valueOf(c);
            harf.setText(ch);
            if (ch.equals("ء")) {
                choice1.setText("همزة محققة");
                choice2.setText("همزة مسهلة");
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else if (ch.equals("ر") || ch.equals("ل")) {
                choice1.setText("مفخم");
                choice2.setText("مرقق");
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                radioGroup1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            }
        }
    }

}
