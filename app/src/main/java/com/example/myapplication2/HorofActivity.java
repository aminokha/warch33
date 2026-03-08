package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.StringCharacterIterator;
import java.util.Timer;
import java.util.TimerTask;

public class HorofActivity extends AppCompatActivity {
    PlayerView playerView;
    Player player;
    FrameLayout videoLayout;
    SeekBar seekBar;
    EditText repeat;
    TextView harf;
    MaterialButton radioButtonFatha;
    MaterialButton radioButtonThama;
    MaterialButton radioButtonKasra;
    MaterialButton radioButtonSokoun;
    MaterialButton choice1;
    MaterialButton choice2;
    StringCharacterIterator stringCharacterIterator;
    TextView message;
    private MaterialButtonToggleGroup toggleGroupChoice;
    private MaterialButtonToggleGroup toggleGroupHarakat;
    private int messageOrdre = 1;
    private SharedPreferences sharedPreferences;
    private static final long BACK_PRESS_INTERVAL_MS = 2000;
    private long lastBackPressedTime;
    private boolean isUpdatingFromSeekBar = false;
    private boolean isUpdatingFromEditText = false;
    private boolean isItemChanged = false;

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.pause();
        }
        saveStat();
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
        if (player != null)
            return;
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    StatsManager.getInstance(HorofActivity.this).incrementHorofCount();
                    play();
                }
            }
        });
        setStat();
    }

    @OptIn(markerClass = UnstableApi.class)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.horof_layout);
        stringCharacterIterator = new StringCharacterIterator("ءبتثجحخدذرزسشصضطظعغفقكلمنهوي");
        message = findViewById(R.id.message);
        playerView = findViewById(R.id.HorofVideoView);
        videoLayout = findViewById(R.id.videoLayout1);
        // replace reference to repeat TextView id to avoid clash with other layouts
        repeat = findViewById(R.id.repeat_horof);
        harf = findViewById(R.id.harf);
        toggleGroupChoice = findViewById(R.id.toggle_group_choice);
        toggleGroupHarakat = findViewById(R.id.harakat_toggle_group);
        radioButtonFatha = findViewById(R.id.radioButtonFatha);
        radioButtonThama = findViewById(R.id.radioButtonThama);
        radioButtonKasra = findViewById(R.id.radioButtonKasra);
        radioButtonSokoun = findViewById(R.id.radioButtonSokoun);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        seekBar = findViewById(R.id.seekBar);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // initializePlayer() call removed as it's handled in onStart

        Timer myTimer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                HorofActivity.this.runOnUiThread(() -> changeMessage());
            }
        };

        myTimer.schedule(myTask, 0l, 1 * (60 * 1000));

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (player != null && player.isPlaying()) {
                    Toast.makeText(HorofActivity.this, "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null && player.isPlaying()) {
                    return;
                }
                isUpdatingFromSeekBar = true;
                repeat.setText(String.valueOf(progress));
                isUpdatingFromSeekBar = false;
            }
        });

        // المستمعات للتحكم في التغيير الفوري للصوت عند الاختيار
        toggleGroupChoice.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked)
                toggleGroupChoice.post(this::checkWhatSound);
        });
        toggleGroupHarakat.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked)
                toggleGroupHarakat.post(this::checkWhatSound);
        });

        // إعداد مراقب النص لـ EditText تكرار - السماح بالكتابة المباشرة دون Dialog
        repeat.addTextChangedListener(new android.text.TextWatcher() {
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
                            repeat.setText("1000");
                        } else if (value < 1) {
                            repeat.setText("1");
                        }
                    } catch (NumberFormatException e) {
                        // تجاهل الأخطاء أثناء الكتابة
                    }
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        // إظهار لوحة المفاتيح عند الضغط على repeat
        repeat.setOnClickListener(v -> {
            if (!repeat.isEnabled()) {
                Toast.makeText(this, "يرجى إيقاف الفيديو أولاً لتتمكن من تغيير التكرار", Toast.LENGTH_SHORT).show();
                return;
            }
            // اختيار كل النص وإظهار لوحة المفاتيح
            repeat.selectAll();
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(repeat, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
            }
        });

        com.google.android.material.bottomnavigation.BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_horof);
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_horof) {
                return true;
            } else if (itemId == R.id.nav_souar) {
                startActivity(new Intent(getApplicationContext(), SouarActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            } else if (itemId == R.id.nav_stats) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                        player.play();
                        disableRepeatControls();
                        playerView.showController();
                    }
                }
            }
            return true;
        });

        // استدعاء setStat moved to initializeController
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStat();
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

    private void saveStat() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("harf", harf.getText().toString());
        if (choice1.isChecked()) {
            editor.putInt("choice_1_or_2", 1);
        } else {
            editor.putInt("choice_1_or_2", 2);
        }
        if (radioButtonFatha.isChecked()) {
            editor.putInt("haraka_type", 1);
        } else if (radioButtonThama.isChecked()) {
            editor.putInt("haraka_type", 2);
        } else if (radioButtonKasra.isChecked()) {
            editor.putInt("haraka_type", 3);
        } else {
            editor.putInt("haraka_type", 4);
        }
        editor.putInt("seekbar_value", seekBar.getProgress());
        editor.apply();
    }

    private void setStat() {
        String harf_local = sharedPreferences.getString("harf", "ء");
        int choice_1_or_2 = sharedPreferences.getInt("choice_1_or_2", 1);
        int haraka_type = sharedPreferences.getInt("haraka_type", 1);
        int seekbar_value = sharedPreferences.getInt("seekbar_value", 50);

        while (!harf.getText().equals(harf_local)) {
            nextHarf(harf);
        }

        updateRadioGroupVisibility(harf_local);
        if (haraka_type == 1) {
            radioButtonFatha.setChecked(true);
        } else if (haraka_type == 2) {
            radioButtonThama.setChecked(true);
        } else if (haraka_type == 3) {
            radioButtonKasra.setChecked(true);
        } else {
            radioButtonSokoun.setChecked(true);
        }
        seekBar.setProgress(seekbar_value);

        checkWhatSound();
        if (seekbar_value > 0 && player != null) {
            if (isItemChanged || !player.isPlaying()) {
                player.seekTo(0);
                player.play();
            }
            disableRepeatControls();
        }
    }

    private void changeMessage() {
        switch (messageOrdre) {
            case 1:
                message.setText("التكرار أساس تعلم أي مهارة");
                messageOrdre++;
                break;
            case 2:
                message.setText(
                        "الماهر بالقرآن مع السفرة الكرام البررة، والذي يقرأ القرآن ويتتعتع فيه وهو عليه شاق له أجران ");
                messageOrdre++;
                break;
            case 3:
                message.setText("والأخذ بالتجويد حتم لازم *** من لم يجود القرآن آثم");
                messageOrdre++;
                break;
            case 4:
                message.setText("كرر بتأن وتركيز");
                messageOrdre++;
                break;
            case 5:
                message.setText("خيركم من تعلم القرآن وعلمه");
                messageOrdre++;
                break;
            case 6:
                message.setText(
                        "من قرأ حرفا من كتاب الله فله به حسنة والحسنة بعشر أمثالها، لا أقول ألم حرف ولكن ألف حرف ولام حرف وميم حرف");
                messageOrdre++;
                break;
            case 7:
                message.setText("أهل القرآن هم أهل الله وخاصته");
                messageOrdre++;
                break;
            case 8:
                message.setText("وليس بينه وبين تركه *** إلا رياضة امرئ بفكه");
                messageOrdre++;
                break;
            default:
                messageOrdre = 1;
                break;
        }
    }

    private void play() {
        int progress = seekBar.getProgress();
        if (progress > 0) {
            seekBar.setProgress(--progress);
            if (player != null) {
                player.seekTo(0);
                player.play();
                disableRepeatControls();
            }
        } else {
            pause();
        }
    }

    private void setPlayerMedia(int resId) {
        if (player == null) {
            isItemChanged = false;
            return;
        }
        String path = "android.resource://" + getPackageName() + "/" + resId;
        Uri uri = Uri.parse(path);

        MediaItem currentItem = player.getCurrentMediaItem();
        if (currentItem != null && currentItem.localConfiguration != null
                && uri.equals(currentItem.localConfiguration.uri)) {
            isItemChanged = false;
            return;
        }

        isItemChanged = true;
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);
        player.prepare();
    }

    private void checkWhatSound() {
        if (player == null)
            return;
        String harfString = harf.getText().toString();
        switch (harfString) {
            case "ء": {
                if (choice2.isChecked()) {
                    radioButtonSokoun.setEnabled(false);
                    if (radioButtonSokoun.isChecked()) {
                        radioButtonKasra.setChecked(true);
                    } else if (radioButtonFatha.isChecked()) {
                        setPlayerMedia(R.raw.hamza_mos_fatha);
                    } else if (radioButtonThama.isChecked()) {
                        setPlayerMedia(R.raw.hamza_mos_dhama);
                    } else if (radioButtonKasra.isChecked()) {
                        setPlayerMedia(R.raw.hamza_mos_kasra);
                    }
                } else {
                    radioButtonSokoun.setEnabled(true);
                    if (radioButtonFatha.isChecked()) {
                        setPlayerMedia(R.raw.hamza_fatha);
                    } else if (radioButtonThama.isChecked()) {
                        setPlayerMedia(R.raw.hamza_dhama2);
                    } else if (radioButtonKasra.isChecked()) {
                        setPlayerMedia(R.raw.hamza_kasra);
                    } else {
                        setPlayerMedia(R.raw.hamza_sokoun);
                    }
                }
                break;
            }
            case "ب": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ba_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ba_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ba_kasra);
                } else {
                    setPlayerMedia(R.raw.ba_sokoun);
                }
                break;
            }
            case "ت": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ta_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ta_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ta_kasra);
                } else {
                    setPlayerMedia(R.raw.ta_sokoun);
                }
                break;
            }
            case "ث": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.tha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.tha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.tha_kasra);
                } else {
                    setPlayerMedia(R.raw.tha_sokoun);
                }
                break;
            }
            case "ج": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.dja_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.dja_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.dja_kasra);
                } else {
                    setPlayerMedia(R.raw.dja_sokoun);
                }
                break;
            }
            case "ح": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.h7a_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.h7a_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.h7a_kasra);
                } else {
                    setPlayerMedia(R.raw.h7a_sokoun);
                }
                break;
            }
            case "خ": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.kha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.kha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.kha_kasra);
                } else {
                    setPlayerMedia(R.raw.kha_sokoun);
                }
                break;
            }
            case "د": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.da_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.da_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.da_kasra);
                } else {
                    setPlayerMedia(R.raw.da_sokoun);
                }
                break;
            }
            case "ذ": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.dha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.dha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.dha_kasra);
                } else {
                    setPlayerMedia(R.raw.dha_sokoun);
                }
                break;
            }
            case "ر": {
                if (radioButtonFatha.isChecked()) {
                    if (choice1.isChecked()) {
                        setPlayerMedia(R.raw.ra_fathaaa);
                    } else {
                        setPlayerMedia(R.raw.ra_fatha);
                    }
                } else if (radioButtonThama.isChecked()) {
                    if (choice1.isChecked()) {
                        setPlayerMedia(R.raw.ra_dhamaaa2);
                    } else {
                        setPlayerMedia(R.raw.ra_dhama);
                    }
                } else if (radioButtonKasra.isChecked()) {
                    choice2.setChecked(true);
                    setPlayerMedia(R.raw.ra_kasra);
                } else {
                    if (choice1.isChecked()) {
                        setPlayerMedia(R.raw.ra_sokounnn);
                    } else {
                        setPlayerMedia(R.raw.ra_sokoun);
                    }
                }
                break;
            }
            case "ز": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.za_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.za_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.za_kasra);
                } else {
                    setPlayerMedia(R.raw.za_sokoun);
                }
                break;
            }
            case "س": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.sa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.sa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.sa_kasra);
                } else {
                    setPlayerMedia(R.raw.sa_sokoun);
                }
                break;
            }
            case "ش": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.sha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.sha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.sha_kasra);
                } else {
                    setPlayerMedia(R.raw.sha_sokoun);
                }
                break;
            }
            case "ص": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.saa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.saa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.saa_kasra);
                } else {
                    setPlayerMedia(R.raw.saa_sokoun);
                }
                break;
            }
            case "ض": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.daa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.daa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.daa_kasra);
                } else {
                    setPlayerMedia(R.raw.daa_sokoun);
                }
                break;
            }
            case "ط": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.taa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.taa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.taa_kasra);
                } else {
                    setPlayerMedia(R.raw.taa_sokoun);
                }
                break;
            }
            case "ظ": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.dhaa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.dhaa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.dhaa_kasra);
                } else {
                    setPlayerMedia(R.raw.dhaa_sokoun);
                }
                break;
            }
            case "ع": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.aa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.aa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.aa_kasra);
                } else {
                    setPlayerMedia(R.raw.aa_sokoun);
                }
                break;
            }
            case "غ": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.gha_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.gha_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.gha_kasra);
                } else {
                    setPlayerMedia(R.raw.gha_sokoun);
                }
                break;
            }
            case "ف": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.fa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.fa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.fa_kasra);
                } else {
                    setPlayerMedia(R.raw.fa_sokoun);
                }
                break;
            }
            case "ق": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.kaa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.kaa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.kaa_kasra);
                } else {
                    setPlayerMedia(R.raw.kaa_sokoun);
                }
                break;
            }
            case "ك": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ka_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ka_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ka_kasra);
                } else {
                    setPlayerMedia(R.raw.ka_sokoun);
                }
                break;
            }
            case "ل": {
                if (radioButtonFatha.isChecked()) {
                    if (choice1.isChecked()) {
                        setPlayerMedia(R.raw.la_fatha2);
                    } else {
                        setPlayerMedia(R.raw.la_fatha);
                    }
                } else if (radioButtonThama.isChecked()) {
                    choice2.setChecked(true);
                    setPlayerMedia(R.raw.la_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    choice2.setChecked(true);
                    setPlayerMedia(R.raw.la_kasra);
                } else {
                    if (choice1.isChecked()) {
                        setPlayerMedia(R.raw.la_sokoun2);
                    } else {
                        setPlayerMedia(R.raw.la_sokoun);
                    }
                }
                break;
            }
            case "م": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ma_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ma_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ma_kasra);
                } else {
                    setPlayerMedia(R.raw.ma_sokoun);
                }
                break;
            }
            case "ن": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.na_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.na_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.na_kasra);
                } else {
                    setPlayerMedia(R.raw.na_sokoun);
                }
                break;
            }
            case "ه": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ha_fatha2);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ha_dhama2);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ha_kasra);
                } else {
                    setPlayerMedia(R.raw.ha_sokoun);
                }
                break;
            }
            case "و": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.wa_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.wa_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.wa_kasra);
                } else {
                    setPlayerMedia(R.raw.wa_sokoun);
                }
                break;
            }
            case "ي": {
                if (radioButtonFatha.isChecked()) {
                    setPlayerMedia(R.raw.ya_fatha);
                } else if (radioButtonThama.isChecked()) {
                    setPlayerMedia(R.raw.ya_dhama);
                } else if (radioButtonKasra.isChecked()) {
                    setPlayerMedia(R.raw.ya_kasra3);
                } else {
                    setPlayerMedia(R.raw.ya_sokoun);
                }
                break;
            }
        }
    }

    private void pause() {
        if (player != null) {
            player.pause();
        }
        enableRepeatControls();
        radioButtonFatha.setEnabled(true);
        radioButtonThama.setEnabled(true);
        radioButtonKasra.setEnabled(true);
        radioButtonSokoun.setEnabled(true);
    }

    public void nextHarf(View view) {
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.button_click_anim));
        if (stringCharacterIterator.getIndex() < stringCharacterIterator.getEndIndex() - 1) {
            char c = stringCharacterIterator.next();
            String ch = String.valueOf(c);
            harf.setText(ch);
            updateRadioGroupVisibility(ch);
            checkWhatSound();
        }
    }

    public void previousHarf(View view) {
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.button_click_anim));
        if (stringCharacterIterator.getIndex() > stringCharacterIterator.getBeginIndex()) {
            char c = stringCharacterIterator.previous();
            String ch = String.valueOf(c);
            harf.setText(ch);
            updateRadioGroupVisibility(ch);
            checkWhatSound();
        }
    }

    /**
     * تعطيل عناصر التحكم بالتكرار (SeekBar و EditText) عند تشغيل الفيديو
     */
    private void disableRepeatControls() {
        seekBar.setEnabled(false);
        repeat.setEnabled(false);
        repeat.setFocusable(false);
        repeat.setFocusableInTouchMode(false);
        // تعديل اللون لجعل العناصر المعطلة تبدو مختلفة
        seekBar.setAlpha(0.5f);
        repeat.setAlpha(0.5f);
    }

    /**
     * تفعيل عناصر التحكم بالتكرار عند إيقاف الفيديو
     */
    private void enableRepeatControls() {
        seekBar.setEnabled(true);
        repeat.setEnabled(true);
        repeat.setFocusable(true);
        repeat.setFocusableInTouchMode(true);
        // استعادة اللون الطبيعي
        seekBar.setAlpha(1.0f);
        repeat.setAlpha(1.0f);
    }

    /**
     * تحديث رؤية خيارات الهمزة (محققة/مسهلة) بحيث تظهر فقط عند اختيار حرف الهمزة.
     * وتختفي مع باقي الحروف بما فيها الراء واللام بناء على طلب المستخدم.
     */
    private void updateRadioGroupVisibility(String harfChar) {
        if (harfChar.equals("ء")) {
            choice1.setText("همزة محققة");
            choice2.setText("همزة مسهلة");
            toggleGroupChoice.setVisibility(View.VISIBLE);
            toggleGroupChoice.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            toggleGroupChoice.setVisibility(View.GONE);
            toggleGroupChoice.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

}
