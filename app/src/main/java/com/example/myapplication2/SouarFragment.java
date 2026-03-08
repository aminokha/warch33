package com.example.myapplication2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButtonToggleGroup;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SouarFragment extends Fragment {
    private ExoPlayer player;
    private PlayerView playerView;
    FrameLayout videoLayout;
    SeekBar seekBar;
    EditText repeat_textView;
    TextView sura_name;
    MaterialButtonToggleGroup toggleGroupAya;
    View ayaSelectionLayout;
    EditText aya_number;
    TextView message;
    RecyclerView recyclerViewSuras;
    SurahAdapter surahAdapter;
    List<Surah> surahList = new ArrayList<>();
    private int messageOrdre = 1;
    private Timer myTimer;
    private boolean isUpdatingFromSeekBar = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.souar_layout, container, false);

        message = view.findViewById(R.id.message);
        playerView = view.findViewById(R.id.playerView);
        videoLayout = view.findViewById(R.id.videoLayout2);
        seekBar = view.findViewById(R.id.progress_bar);
        repeat_textView = view.findViewById(R.id.repeat);
        sura_name = view.findViewById(R.id.sura_name);
        aya_number = view.findViewById(R.id.aya_number);
        toggleGroupAya = view.findViewById(R.id.toggle_group_aya);
        ayaSelectionLayout = view.findViewById(R.id.aya_selection_layout);
        recyclerViewSuras = view.findViewById(R.id.recyclerViewSuras);

        initializePlayer();
        initializeCard();

        myTimer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                if (isAdded()) {
                    getActivity().runOnUiThread(() -> changeMessage());
                }
            }
        };
        myTimer.scheduleAtFixedRate(myTask, 0l, 1 * (60 * 1000));

        toggleGroupAya.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.choice_specific_aya) {
                    aya_number.setEnabled(true);
                    if (ayaSelectionLayout != null)
                        ayaSelectionLayout.setVisibility(View.VISIBLE);
                } else {
                    aya_number.setText("");
                    aya_number.setEnabled(false);
                    if (ayaSelectionLayout != null)
                        ayaSelectionLayout.setVisibility(View.GONE);
                }
            }
        });

        aya_number.setOnEditorActionListener((textView, i, keyEvent) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aya_number.getWindowToken(), 0);
            play(textView);
            return true;
        });

        repeat_textView.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repeat_textView.isEnabled() && !isUpdatingFromSeekBar && !s.toString().isEmpty()) {
                    try {
                        int value = Integer.parseInt(NumberUtils.normalize(s.toString()));
                        if (value >= 1 && value <= 1000) {
                            seekBar.setProgress(value);
                        } else if (value > 1000) {
                            repeat_textView.setText("1000");
                        } else if (value < 1) {
                            repeat_textView.setText("1");
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        repeat_textView.setOnClickListener(v -> {
            if (!repeat_textView.isEnabled()) {
                Toast.makeText(requireContext(), "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            repeat_textView.selectAll();
            InputMethodManager imm = (InputMethodManager) requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(repeat_textView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (player != null && player.isPlaying()) {
                    Toast.makeText(requireContext(), "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null && player.isPlaying()) {
                    return;
                }
                isUpdatingFromSeekBar = true;
                repeat_textView.setText(String.valueOf(progress));
                isUpdatingFromSeekBar = false;
            }
        });

        playerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.performClick();
                if (player != null) {
                    if (player.isPlaying()) {
                        player.pause();
                        enableRepeatControls();
                    } else {
                        // التحقق من المدخلات قبل البدء
                        if (toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya
                                && aya_number.getText().toString().isEmpty()) {
                            Toast.makeText(requireContext(), "يرجى تحديد رقم الآية أولاً", Toast.LENGTH_SHORT).show();
                        } else {
                            startPlayback();
                            play(null); // تحديث المسار بناء على الاختيار
                        }
                    }
                }
            }
            return false;
        });

        setVideoUri(R.raw.fatiha);

        return view;
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    StatsManager.getInstance(requireContext()).incrementSouarCount();
                    int progress = seekBar.getProgress();
                    if (progress > 0) {
                        seekBar.setProgress(progress - 1);
                        player.seekTo(0);
                        player.play();
                    } else {
                        enableRepeatControls();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (myTimer != null) {
            myTimer.cancel();
        }
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void changeMessage() {
        switch (messageOrdre) {
            case 1:
                message.setText("'' التكرار أساس تعلم أي مهارة ''");
                messageOrdre++;
                break;
            case 2:
                message.setText(
                        "'' الماهر بالقرآن مع السفرة الكرام البررة، والذي يقرأ القرآن ويتتعتع فيه وهو عليه شاق له أجران '' ");
                messageOrdre++;
                break;
            case 3:
                message.setText("'' والأخذ بالتجويد حتم لازم *** من لم يجود القرآن آثم ''");
                messageOrdre++;
                break;
            case 4:
                message.setText("'' كرر بتأن وتركيز ''");
                messageOrdre++;
                break;
            case 5:
                message.setText("'' خيركم من تعلم القرآن وعلمه ''");
                messageOrdre++;
                break;
            case 6:
                message.setText(
                        "'' من قرأ حرفا من كتاب الله فله به حسنة والحسنة بعشر أمثالها، لا أقول ألم حرف ولكن ألف حرف ولام حرف وميم حرف ''");
                messageOrdre++;
                break;
            case 7:
                message.setText("'' أهل القرآن هم أهل الله وخاصته ''");
                messageOrdre++;
                break;
            case 8:
                message.setText("'' وليس بينه وبين تركه *** إلا رياضة امرئ بفكه ''");
                messageOrdre++;
                break;
            default:
                messageOrdre = 1;
                break;
        }
    }

    private void play(View view) {
        if (toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya) {
            String currentSuraName = sura_name.getText().toString();
            String ayaNumStr = NumberUtils.normalize(aya_number.getText().toString());
            if (ayaNumStr.isEmpty())
                return;

            try {
                int ayaNum = Integer.parseInt(ayaNumStr);
                for (Surah s : SurahRepository.getSurahList()) {
                    if (s.getDisplayName().equals(currentSuraName)) {
                        if (ayaNum >= 1 && ayaNum <= s.getMaxAyahCount()) {
                            String resName = s.getInternalName() + "_" + ayaNum;
                            int resId = getResources().getIdentifier(resName, "raw", requireContext().getPackageName());
                            if (resId != 0) {
                                setVideoUri(resId);
                                startPlayback();
                            } else {
                                Toast.makeText(requireContext(), "فيديو هذه الآية غير متوفر حالياً", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "يرجى إدخال رقم آية من 1 إلى " + s.getMaxAyahCount(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                return;
            }
        } else {
            startPlayback();
        }
    }

    private void startPlayback() {
        if (player != null) {
            player.play();
            disableRepeatControls();
        }
    }

    private void setVideoUri(int resId) {
        if (player != null) {
            String path = "android.resource://" + requireContext().getPackageName() + "/" + resId;
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(path));
            player.setMediaItem(mediaItem);
            player.prepare();
        }
    }

    private void initializeCard() {
        surahList.clear();
        surahList.addAll(SurahRepository.getSurahList());

        surahAdapter = new SurahAdapter(surahList, (surah, view) -> {
            sura_name.setText(surah.getDisplayName());
            aya_number.setText("");
            setVideoUri(surah.getVideoResId());
            startPlayback();
            if (toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya) {
                toggleGroupAya.check(R.id.choice_full_sura);
            }
        });

        recyclerViewSuras
                .setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSuras.setAdapter(surahAdapter);
    }

    private void disableRepeatControls() {
        seekBar.setEnabled(false);
        repeat_textView.setEnabled(false);
        repeat_textView.setFocusable(false);
        repeat_textView.setFocusableInTouchMode(false);
        seekBar.setAlpha(0.5f);
        repeat_textView.setAlpha(0.5f);
    }

    private void enableRepeatControls() {
        seekBar.setEnabled(true);
        repeat_textView.setEnabled(true);
        repeat_textView.setFocusable(true);
        repeat_textView.setFocusableInTouchMode(true);
        seekBar.setAlpha(1.0f);
        repeat_textView.setAlpha(1.0f);
    }
}
