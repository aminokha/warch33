package com.example.myapplication2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.internal.ViewUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

public class Main3Activity extends AppCompatActivity {
    VideoView videoView;
    FrameLayout videoLayout;
    MediaController mediaController;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    TextView repeat_textView;
    TextView sura_name;
    SwitchCompat switch_only_aya;
    EditText aya_number;
    CardView fatiha_card;
    CardView annas_card;
    CardView alfalk_card;
    CardView alikhlas_card;
    CardView alfil_card;
    CardView alhomaza_card;
    CardView albaiina_card;
    CardView alkader_card;
    CardView alalak_card;
    CardView acharh_card;


    ImageButton play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        videoView = findViewById(R.id.videoView);
        videoLayout = findViewById(R.id.videoLayout);
        seekBar = findViewById(R.id.progress_bar);
        repeat_textView = findViewById(R.id.repeat);
        sura_name = findViewById(R.id.sura_name);
        aya_number = findViewById(R.id.aya_number);
        switch_only_aya = findViewById(R.id.chip4);
        fatiha_card = findViewById(R.id.fatiha_card);
        annas_card = findViewById(R.id.annas_card);
        alfalk_card = findViewById(R.id.alfalak);
        alikhlas_card = findViewById(R.id.alikhlas);
        alfil_card = findViewById(R.id.alfil);
        alhomaza_card = findViewById(R.id.alhomaza);
        albaiina_card = findViewById(R.id.albaiina);
        alkader_card = findViewById(R.id.alkader);
        alalak_card = findViewById(R.id.alalak);
        acharh_card = findViewById(R.id.acharh);
        play_button = findViewById(R.id.play_button);
        mediaController = new MediaController(this);
        videoView.setOnCompletionListener(mediaPlayer1 -> {
            seekBar.setProgress(seekBar.getProgress() - 1);
            repeat(mediaPlayer1);

        });
        switch_only_aya.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                aya_number.setEnabled(true);
                aya_number.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                aya_number.setText("");
                aya_number.setEnabled(false);
                aya_number.setBackgroundColor(Color.parseColor("#999999"));
            }
        });
        aya_number.setOnEditorActionListener((textView, i, keyEvent) -> {
            videoView.pause();
            play_button.setImageResource(R.drawable.play_icon);
            play_button.setTag("play");
            play_button.performClick();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aya_number.getWindowToken(),0);
            return true;
        });
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
                repeat_textView.setText(String.valueOf(progress));
            }
        });
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha);
        initializeCard();
        play_button.setOnClickListener(this::play_or_pause);

        mediaController.setAnchorView(videoLayout);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);
    }

    private void play_or_pause(View view) {
//        if (play_button.getTag().toString().equals("play")) {
        if (!videoView.isPlaying()) {
            play(view);
        } else {
            videoView.pause();
            play_button.setImageResource(R.drawable.play_icon);
            play_button.setTag("play");
        }
    }

    private void play(View view) {
        if (switch_only_aya.isChecked()) {
            // معرفة السورة المختارة
            switch (sura_name.getText().toString()) {
                case "ســورة الفـاتـحـة": {
                    // معرفة رقم الآية المختارة
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_7);
                        }
                        break;

                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 7  ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة النــاس": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas_6);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 6 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة الفـلق": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak_5);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 5 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة الاخـلاص": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas_4);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 4 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة الفيــل": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil_5);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 5 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة الهمـزة": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_8);
                        }
                        break;
                        case "9": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza_9);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 9 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة البيـنة": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina_8);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 8 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة القـدر": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader_5);
                        }
                        break;

                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 4 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة العـلق": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_8);
                        }
                        break;
                        case "9": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_9);
                        }
                        break;
                        case "10": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_10);
                        }
                        break;
                        case "11": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_11);
                        }
                        break;
                        case "12": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_12);
                        }
                        break;
                        case "13": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_13);
                        }
                        break;
                        case "14": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_14);
                        }
                        break;
                        case "15": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_15);
                        }
                        break;
                        case "16": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_16);
                        }
                        break;
                        case "17": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_17);
                        }
                        break;
                        case "18": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_18);
                        }
                        break;
                        case "19": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_19);
                        }
                        break;
                        case "20": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak_20);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 20 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة الشـرح": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh_8);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 8 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
            }
        }

        videoView.start();
        play_button.setImageResource(R.drawable.pause_icon);
        play_button.setTag("pause");

    }

    private void repeat(MediaPlayer mediaPlayer) {
        int progress = seekBar.getProgress();
        if (progress > 0) {
//            seekBar.setProgress(--progress);
            mediaPlayer.start();
        } else {
            play_button.setImageResource(R.drawable.play_icon);
            play_button.setTag("play");
        }

    }

    private void initializeCard() {
        fatiha_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفـاتـحـة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        annas_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة النــاس");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alfalk_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفـلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alikhlas_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الاخـلاص");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alfil_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفيــل");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alhomaza_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الهمـزة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        albaiina_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة البيـنة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alkader_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة القـدر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alalak_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة العـلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        acharh_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الشـرح");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
    }

    private void animateCard(View view) {
        view.animate()
                .scaleX(1.2F).scaleY(1.2F)
                .setDuration(200)
                .setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view.setScaleX(1);
                        view.setScaleY(1);
                    }
                });
    }

//    public void pause(View view) {
//        if (videoView.isPlaying()) {
//            videoView.pause();
//            playButton.setImageResource(R.drawable.play_icon);
//        } else {
//            videoView.start();
//            playButton.setImageResource(R.drawable.pause_icon);
//        }
//    }

}
