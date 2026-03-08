package com.example.myapplication2;

public class Surah {
    private String name;
    private int videoResId;
    private int imageResId;
    private String displayName; // The name shown in the Activity title
    private String internalName; // The name used for resource files (e.g., "fatiha")
    private int maxAyahCount; // Number of available ayah videos

    public Surah(String name, String displayName, String internalName, int videoResId, int imageResId,
            int maxAyahCount) {
        this.name = name;
        this.displayName = displayName;
        this.internalName = internalName;
        this.videoResId = videoResId;
        this.imageResId = imageResId;
        this.maxAyahCount = maxAyahCount;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getInternalName() {
        return internalName;
    }

    public int getVideoResId() {
        return videoResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getMaxAyahCount() {
        return maxAyahCount;
    }
}
