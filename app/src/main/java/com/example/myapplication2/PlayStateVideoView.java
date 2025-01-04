package com.example.myapplication2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.VideoView;

public class PlayStateVideoView extends VideoView {
    private SeekBar seekBar;

    public PlayStateVideoView(Context context) {
        super(context);
    }

    public PlayStateVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayStateVideoView(Context context, AttributeSet attrs, int theme) {
        super(context, attrs, theme);
    }

    public void setSeekBar(SeekBar seekBar) { // Example setter method
        this.seekBar = seekBar;
    }

    @Override
    public void pause() {
        super.pause();
        seekBar.setEnabled(true);
    }

    @Override
    public void start() {
        super.start();
        seekBar.setEnabled(false);
    }
}
