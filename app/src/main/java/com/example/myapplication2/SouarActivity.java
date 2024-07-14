package com.example.myapplication2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

public class SouarActivity extends AppCompatActivity {
    VideoView videoView;
    FrameLayout videoLayout;
    MediaController mediaController;
    SeekBar seekBar;
    TextView repeat_textView;
    TextView sura_name;
    SwitchCompat switch_only_aya;
    EditText aya_number;
    CardView fatiha_card;
    CardView annas_card;
    CardView alfalk_card;
    CardView alikhlas_card;
    CardView almassad_card;
    CardView annasr_card;
    CardView alkafiroun_card;
    CardView alkawthar_card;
    CardView almaoun_card;
    CardView koraich_card;
    CardView alfil_card;
    CardView alhomaza_card;
    CardView alasr_card;
    CardView attakator_card;
    CardView alkariaa_card;
    CardView aladiate_card;
    CardView azzalzala_card;
    CardView albaiina_card;
    CardView alkader_card;
    CardView alalak_card;
    CardView attine_card;
    CardView acharh_card;
    LinearLayout card_layout;
    HorizontalScrollView scrollView;
    TextView message;
    private int messageOrdre = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.souar_layout);
        message = findViewById(R.id.message);
        videoView = findViewById(R.id.videoView);
        videoLayout = findViewById(R.id.videoLayout);
        seekBar = findViewById(R.id.progress_bar);
        repeat_textView = findViewById(R.id.repeat);
        sura_name = findViewById(R.id.sura_name);
        aya_number = findViewById(R.id.aya_number);
        switch_only_aya = findViewById(R.id.chip4);
        card_layout = findViewById(R.id.card_layout);
        scrollView = findViewById(R.id.scrollView1);
        fatiha_card = findViewById(R.id.fatiha_card);
        annas_card = findViewById(R.id.annas_card);
        alfalk_card = findViewById(R.id.alfalak);
        alikhlas_card = findViewById(R.id.alikhlas);
        almassad_card = findViewById(R.id.almassad);
        annasr_card = findViewById(R.id.annasr);
        alkafiroun_card = findViewById(R.id.alkafiroun);
        alkawthar_card = findViewById(R.id.alkawthar);
        almaoun_card = findViewById(R.id.almaoun);
        koraich_card = findViewById(R.id.koraich);
        alfil_card = findViewById(R.id.alfil);
        alhomaza_card = findViewById(R.id.alhomaza);
        alasr_card = findViewById(R.id.alasr);
        attakator_card = findViewById(R.id.attakathor);
        alkariaa_card = findViewById(R.id.alkariaa);
        aladiate_card = findViewById(R.id.aladiat);
        azzalzala_card = findViewById(R.id.azzalzala);
        albaiina_card = findViewById(R.id.albaiina);
        alkader_card = findViewById(R.id.alkader);
        alalak_card = findViewById(R.id.alalak);
        attine_card = findViewById(R.id.attine);
        acharh_card = findViewById(R.id.acharh);

        Timer myTimer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                SouarActivity.this.runOnUiThread(() ->changeMessage());
            }
        };
        myTimer.scheduleAtFixedRate(myTask, 0l, 1 * (60 * 1000));

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
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aya_number.getWindowToken(), 0);
            play(textView);
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        resizeCards(fatiha_card, height / 7);
        resizeCards(annas_card, height / 7);
        resizeCards(alfalk_card, height / 7);
        resizeCards(alikhlas_card, height / 7);
        resizeCards(almassad_card, height / 7);
        resizeCards(annasr_card, height / 7);
        resizeCards(alkafiroun_card, height / 7);
        resizeCards(alkawthar_card, height / 7);
        resizeCards(almaoun_card, height / 7);
        resizeCards(koraich_card, height / 7);
        resizeCards(alfil_card, height / 7);
        resizeCards(alhomaza_card, height / 7);
        resizeCards(alasr_card, height / 7);
        resizeCards(attakator_card, height / 7);
        resizeCards(alkariaa_card, height / 7);
        resizeCards(aladiate_card, height / 7);
        resizeCards(azzalzala_card, height / 7);
        resizeCards(albaiina_card, height / 7);
        resizeCards(alkader_card, height / 7);
        resizeCards(alalak_card, height / 7);
        resizeCards(attine_card, height / 7);
        resizeCards(acharh_card, height / 7);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//        mediaController.setAnchorView(videoView);

        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setOnVideoSizeChangedListener((mediaPlayer1, width, heightt) -> {
                mediaController = new MediaController(SouarActivity.this);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    mediaController.setTransitionAlpha(0.5f);
                } else {
                    mediaController.setAlpha(0.5f);
                }
            });
        });
    }

    private void changeMessage() {
        switch (messageOrdre) {
            case 1:
                message.setText("التكرار أساس تعلم أي مهارة");
                messageOrdre++;
                break;
            case 2:
                message.setText("الماهر بالقرآن مع السفرة الكرام البررة، والذي يقرأ القرآن ويتتعتع فيه وهو عليه شاق له أجران ");
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
                message.setText("من قرأ حرفا من كتاب الله فله به حسنة والحسنة بعشر أمثالها، لا أقول ألم حرف ولكن ألف حرف ولام حرف وميم حرف");
                messageOrdre++;
                break;
            case 7:
                message.setText("أهل القرآن هم أهل الله وخاصته");
                messageOrdre++;
                break;
            case 8:
                message.setText("وليس بينه وبين تركه *** رياضة امرئ بفكه");
                messageOrdre++;
                break;
            default:
                messageOrdre = 1;
                break;
        }
    }

    private void resizeCards(CardView card, int width) {
        card.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        card.requestLayout();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
        layoutParams.setMargins(10, 0, 10, 0);
        card.requestLayout();
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

                case "ســورة المـسـد": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad_5);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 5 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة النـصـر": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annasr_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annasr_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annasr_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annasr_4);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 4 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة الكـافـرون": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun_6);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 6 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة الكـوثـر": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkawthar_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkawthar_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkawthar_3);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 3", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة المـاعـون": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun_7);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 7", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة قـريـش": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich_5);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 5", Toast.LENGTH_LONG).show();
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

                case "ســورة العـصـر": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alasr_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alasr_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alasr_3);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 3 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;
                case "ســورة التكـاثـر": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor_8);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 8 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة القـارعـة": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_8);
                        }
                        break;
                        case "9": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_9);
                        }
                        break;
                        case "10": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa_10);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 10 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة العـاديـات": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_8);
                        }
                        break;
                        case "9": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_9);
                        }
                        break;
                        case "10": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_10);
                        }
                        break;
                        case "11": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat_11);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 11 ", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                break;

                case "ســورة الزلـزلـة": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_8);
                        }
                        break;
                        case "9": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala_9);
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
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 5 ", Toast.LENGTH_LONG).show();
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

                case "ســورة التـيـن": {
                    switch (aya_number.getText().toString()) {
                        case "1": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_1);
                        }
                        break;
                        case "2": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_2);
                        }
                        break;
                        case "3": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_3);
                        }
                        break;
                        case "4": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_4);
                        }
                        break;
                        case "5": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_5);
                        }
                        break;
                        case "6": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_6);
                        }
                        break;
                        case "7": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_7);
                        }
                        break;
                        case "8": {
                            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine_8);
                        }
                        break;
                        default:
                            Toast.makeText(this, "يرجى إدخال رقم آية من 1 إلى 8 ", Toast.LENGTH_LONG).show();
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
    }


    private void repeat(MediaPlayer mediaPlayer) {
        int progress = seekBar.getProgress();
        if (progress > 0) {
            mediaPlayer.start();
        }
    }

    private void initializeCard() {
        fatiha_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفـاتـحـة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fatiha);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        annas_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة النــاس");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annas);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alfalk_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفـلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alikhlas_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الاخـلاص");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alikhlas);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        almassad_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة المـسـد");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almassad);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        annasr_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة النـصـر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.annasr);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alkafiroun_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الكـافـرون");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkafiroun);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alkawthar_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الكـوثـر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkawthar);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });

        almaoun_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة المـاعـون");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.almaoun);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });

        koraich_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة قـريـش");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.koraich);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alfil_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الفيــل");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alfil);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alhomaza_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الهمـزة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alhomaza);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alasr_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة العـصـر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alasr);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        attakator_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة التكـاثـر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attakathor);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alkariaa_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة القـارعـة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkariaa);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        aladiate_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة العـاديـات");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.aladiat);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        azzalzala_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الزلـزلـة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.azzalzala);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        albaiina_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة البيـنة");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.albaiina);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alkader_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة القـدر");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alkader);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        alalak_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة العـلق");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.alalak);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        attine_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة التـيـن");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.attine);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
            }
        });
        acharh_card.setOnClickListener(view -> {
            animateCard(view);
            sura_name.setText("ســورة الشـرح");
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.acharh);
            if (seekBar.getProgress() > 0) {
                seekBar.setProgress(seekBar.getProgress() - 1);
                videoView.start();
                if (switch_only_aya.isChecked()) {
                    switch_only_aya.performClick();
                }
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


}
