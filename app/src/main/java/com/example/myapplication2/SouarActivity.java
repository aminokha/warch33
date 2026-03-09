package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButtonToggleGroup;
import androidx.cardview.widget.CardView;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SouarActivity extends AppCompatActivity {
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
    SharedPreferences sharedPreferences;
    private boolean isUpdatingFromSeekBar = false;
    private static final long BACK_PRESS_INTERVAL_MS = 2000;
    private long lastBackPressedTime;

    @Override
    protected void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.pause();
        }
        saveState();
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (player != null) {
            player.release();
            player = null;
        }
        if (playerView != null) {
            playerView.setPlayer(null);
        }
        super.onStop();
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playerView.setControllerShowTimeoutMs(1500);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    StatsManager.getInstance(SouarActivity.this).incrementSouarCount();
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
        restoreState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.souar_layout);
        sharedPreferences = getSharedPreferences("souar_prefs", MODE_PRIVATE);
        message = findViewById(R.id.message);
        playerView = findViewById(R.id.playerView);
        videoLayout = findViewById(R.id.videoLayout2);
        seekBar = findViewById(R.id.progress_bar);
        repeat_textView = findViewById(R.id.repeat);
        sura_name = findViewById(R.id.sura_name);
        aya_number = findViewById(R.id.aya_number);
        toggleGroupAya = findViewById(R.id.toggle_group_aya);
        ayaSelectionLayout = findViewById(R.id.aya_selection_layout);
        recyclerViewSuras = findViewById(R.id.recyclerViewSuras);

        // initializePlayer() removed, handled by handleController
        initializeCard();

        Timer myTimer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                SouarActivity.this.runOnUiThread(() -> changeMessage());
            }
        };
        myTimer.scheduleAtFixedRate(myTask, 0l, 1 * (60 * 1000));

        toggleGroupAya.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.choice_specific_aya) {
                    aya_number.setEnabled(true);
                    ayaSelectionLayout.setVisibility(View.VISIBLE);
                } else {
                    aya_number.setText("");
                    aya_number.setEnabled(false);
                    ayaSelectionLayout.setVisibility(View.GONE);
                }
            }
        });

        aya_number.setOnEditorActionListener((textView, i, keyEvent) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                if (!isUpdatingFromSeekBar && !s.toString().isEmpty()) {
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
                Toast.makeText(this, "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار", Toast.LENGTH_SHORT).show();
                return;
            }
            repeat_textView.selectAll();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    Toast.makeText(SouarActivity.this, "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار",
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

        com.google.android.material.bottomnavigation.BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_souar);
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_horof) {
                startActivity(new Intent(getApplicationContext(), HorofActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.nav_souar) {
                return true;
            } else if (itemId == R.id.nav_stats) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });

        playerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (player != null) {
                    if (player.isPlaying()) {
                        player.pause();
                        enableRepeatControls();
                        playerView.showController();
                    } else {
                        // التحقق من المدخلات قبل البدء
                        if (toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya
                                && aya_number.getText().toString().isEmpty()) {
                            Toast.makeText(this, "يرجى تحديد رقم الآية أولاً", Toast.LENGTH_SHORT).show();
                        } else {
                            startPlayback();
                            play(null); // تحديث المسار بناء على الاختيار
                            playerView.showController();
                        }
                    }
                }
            }
            return true;
        });

        // restoreState() moved to initializeController
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastBackPressedTime <= BACK_PRESS_INTERVAL_MS) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = now;
            Toast.makeText(this, "اضغط مرة أخرى للخروج", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sura_name", sura_name.getText().toString());
        editor.putInt("seekbar_progress", seekBar.getProgress());
        editor.putBoolean("repeat_aya_mode", toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya);
        editor.putString("aya_number", aya_number.getText().toString());
        editor.apply();
    }

    private void restoreState() {
        String savedSura = sharedPreferences.getString("sura_name", "ســورة الفـاتـحـة");
        int savedProgress = sharedPreferences.getInt("seekbar_progress", 0);
        boolean repeatAyaMode = sharedPreferences.getBoolean("repeat_aya_mode", false);
        String savedAya = sharedPreferences.getString("aya_number", "");

        sura_name.setText(savedSura);
        seekBar.setProgress(savedProgress);

        if (repeatAyaMode) {
            toggleGroupAya.check(R.id.choice_specific_aya);
            aya_number.setEnabled(true);
            aya_number.setText(savedAya);
            ayaSelectionLayout.setVisibility(View.VISIBLE);
        } else {
            toggleGroupAya.check(R.id.choice_full_sura);
            aya_number.setEnabled(false);
            aya_number.setText("");
            ayaSelectionLayout.setVisibility(View.GONE);
        }

        if (repeatAyaMode && !savedAya.isEmpty()) {
            play(null);
        } else {
            playFullSura(savedSura);
        }
    }

    private void playFullSura(String displayName) {
        for (Surah s : SurahRepository.getSurahList()) {
            if (s.getDisplayName().equals(displayName)) {
                setVideoUri(s.getVideoResId());
                return;
            }
        }
    }

    private void setVideoUri(int resId) {
        if (player != null) {
            String path = "android.resource://" + getPackageName() + "/" + resId;
            Uri uri = Uri.parse(path);

            MediaItem currentItem = player.getCurrentMediaItem();
            if (currentItem != null && currentItem.localConfiguration != null
                    && uri.equals(currentItem.localConfiguration.uri)) {
                return;
            }

            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
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
                            int resId = getResources().getIdentifier(resName, "raw", getPackageName());
                            if (resId != 0) {
                                setVideoUri(resId);
                                startPlayback();
                            } else {
                                Toast.makeText(this, "فيديو هذه الآية غير متوفر حالياً", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى " + s.getMaxAyahCount(),
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

    private void initializeCard() {
        surahList.clear();
        surahList.addAll(SurahRepository.getSurahList());

        surahAdapter = new SurahAdapter(surahList, (surah, view) -> {
            sura_name.setText(surah.getDisplayName());
            setVideoUri(surah.getVideoResId());
            aya_number.setText("");
            startPlayback();
            if (toggleGroupAya.getCheckedButtonId() == R.id.choice_specific_aya) {
                toggleGroupAya.check(R.id.choice_full_sura);
            }
        });

        recyclerViewSuras.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
