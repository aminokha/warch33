package com.example.myapplication2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

public class Main3Activity extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    LinearLayoutCompat videoLayout;
    ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        playButton = findViewById(R.id.playButton);
        videoView = findViewById(R.id.videoView);
        videoLayout = findViewById(R.id.videoLayout);
        mediaController = new MediaController(this);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha);

        mediaController.setAnchorView(videoLayout);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);
        videoView.start();

    }

    public void pause(View view) {
        videoView.pause();
    }
}
