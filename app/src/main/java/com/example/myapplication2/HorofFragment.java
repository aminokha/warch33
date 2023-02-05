package com.example.myapplication2;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.StringCharacterIterator;

public class HorofFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

//    VideoView videoView;
//    FrameLayout videoLayout;
//    SeekBar seekBar;
//    TextView repeat;
    TextView harf;
//    MediaPlayer mediaPlayer;
//    RadioButton radioButtonFatha;
//    RadioButton radioButtonThama;
//    RadioButton radioButtonKasra;
//    RadioButton radioButtonSokoun;
    RadioButton choice1;
    RadioButton choice2;
    StringCharacterIterator stringCharacterIterator;
//    private MediaController mediaController;
    private RadioGroup radioGroup1;

    public HorofFragment() {
        // Required empty public constructor
    }

    public static HorofFragment newInstance(String param1, String param2) {
        HorofFragment fragment = new HorofFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        stringCharacterIterator = new StringCharacterIterator("ءبتثجحخدذرزسشصضطظعغفقكلمنهوي");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_horof, container, false);
        harf = rootView.findViewById(R.id.harf);
//        harf.setOnClickListener(this::nextHarf);
        choice1 = rootView.findViewById(R.id.choice1);
        choice2 = rootView.findViewById(R.id.choice2);
        radioGroup1 = rootView.findViewById(R.id.radio_group1);


        return rootView;
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

    public void ddd(View view) {
    }
}