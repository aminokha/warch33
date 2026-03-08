package com.example.myapplication2;

import java.util.ArrayList;
import java.util.List;

public class SurahRepository {
    private static List<Surah> surahList;

    public static List<Surah> getSurahList() {
        if (surahList == null) {
            surahList = new ArrayList<>();
            surahList.add(new Surah("سورة الفاتحة", "ســورة الفـاتـحـة", "fatiha", R.raw.fatiha, R.drawable.img1, 7));
            surahList.add(new Surah("سورة الناس", "ســورة النــاس", "annas", R.raw.annas, R.drawable.img2, 6));
            surahList.add(new Surah("سورة الفلق", "ســورة الفـلق", "alfalak", R.raw.alfalak, R.drawable.alfalak, 5));
            surahList.add(new Surah("سورة الاخلاص", "ســورة الاخـلاص", "alikhlas", R.raw.alikhlas, R.drawable.img4, 4));
            surahList
                    .add(new Surah("سورة المسد", "ســورة المـسـد", "almassad", R.raw.almassad, R.drawable.almassad, 5));
            surahList.add(new Surah("سورة النصر", "ســورة النـصـر", "annasr", R.raw.annasr, R.drawable.annasr, 4));
            surahList.add(new Surah("سورة الكافرون", "ســورة الكـافـرون", "alkafiroun", R.raw.alkafiroun,
                    R.drawable.alkafiroun, 6));
            surahList.add(
                    new Surah("سورة الكوثر", "ســورة الكـوثـر", "alkawthar", R.raw.alkawthar, R.drawable.alkawthar, 3));
            surahList.add(
                    new Surah("سورة الماعون", "ســورة المـاعـون", "almaoun", R.raw.almaoun, R.drawable.almaoun, 7));
            surahList.add(new Surah("سورة قريش", "ســورة قـريـش", "koraich", R.raw.koraich, R.drawable.koraich, 5));
            surahList.add(new Surah("سورة الفيل", "ســورة الفيــل", "alfil", R.raw.alfil, R.drawable.img5, 5));
            surahList.add(new Surah("سورة الهمزة", "ســورة الهمـزة", "alhomaza", R.raw.alhomaza, R.drawable.img6, 9));
            surahList.add(new Surah("سورة العصر", "ســورة العـصـر", "alasr", R.raw.alasr, R.drawable.img6, 3));
            surahList.add(new Surah("سورة التكاثر", "ســورة التكـاثـر", "attakathor", R.raw.attakathor,
                    R.drawable.attakathor, 8));
            surahList.add(
                    new Surah("سورة القارعة", "ســورة القـارعـة", "alkariaa", R.raw.alkariaa, R.drawable.alkariaa, 10)); // Total 10 available mp4 files.
            surahList.add(
                    new Surah("سورة العاديات", "ســورة العـاديـات", "aladiat", R.raw.aladiat, R.drawable.aladiat, 11));
            surahList.add(new Surah("سورة الزلزلة", "ســورة الزلـزلـة", "azzalzala", R.raw.azzalzala,
                    R.drawable.azzalzala, 8));
            surahList.add(new Surah("سورة البينة", "ســورة البيـنة", "albaiina", R.raw.albaiina, R.drawable.img7, 8));
            surahList.add(new Surah("سورة القدر", "ســورة القـدر", "alkader", R.raw.alkader, R.drawable.img8, 5));
            surahList.add(new Surah("سورة العلق", "ســورة العـلق", "alalak", R.raw.alalak, R.drawable.img9, 20)); // Code
                                                                                                                  // shows
                                                                                                                  // up
                                                                                                                  // to
                                                                                                                  // case
            surahList.add(new Surah("سورة التين", "ســورة التـيـن", "attine", R.raw.attine, R.drawable.img10, 8));
            surahList.add(new Surah("سورة الشرح", "ســورة الشـرح", "acharh", R.raw.acharh, R.drawable.img1, 8));
        }
        return surahList;
    }
}
