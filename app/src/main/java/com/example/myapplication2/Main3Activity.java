package com.example.myapplication2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

public class Main3Activity extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    LinearLayoutCompat videoLayout;
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
            seekBar.setProgress(seekBar.getProgress()-1);
            repeat(mediaPlayer1);
//            play_button.setImageResource(R.drawable.pause_icon);
//            play_button.setTag("pause");

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
        if (((String) play_button.getTag()).equals("play")) {
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
                        case "1":
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha_1);
                            break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم بين 1 و 8  ", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case "ســورة النــاس": {

                }
                break;
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
            sura_name.setText("ســورة الفلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alikhlas_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الاخلاص");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alfil_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفيل");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alhomaza_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الهمزة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        albaiina_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة البينة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alkader_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة القدر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        alalak_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة العلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
//                videoView.start();
            }
        });
        acharh_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الشرح");
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
                .setDuration(500)
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
