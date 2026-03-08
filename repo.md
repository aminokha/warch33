# Homat Warch Repository

## Project Overview

**Homat Warch** is an Android application designed for educational purposes, specifically for learning Arabic letters (Horof) and Quranic Surahs (Souar). It features a video-based learning approach with an intuitive interface and tracking capabilities.

- **Package Name**: `com.example.myapplication2`
- **Project Name**: `homat_warch`
- **Application ID**: `com.example.myapplication2`

## Technical Stack

### Build Configuration
- **Gradle Version**: 8.13.0
- **Compile SDK**: 34
- **Target SDK**: 33
- **Minimum SDK**: 21
- **Java Version**: 17 (Java 1.8 compatibility for some components)
- **Build Features**: View Binding enabled

### Key Dependencies
- **UI & Layout**:
  - `androidx.appcompat:appcompat:1.7.1`
  - `com.google.android.material:material:1.13.0`
  - `androidx.constraintlayout:constraintlayout:2.2.1`
  - `androidx.gridlayout:gridlayout:1.0.0`
  - `com.joooonho:selectableroundedimageview:1.0.1`
  - `com.intuit.sdp:sdp-android:1.1.0` (Scalable DP)
  - `com.intuit.ssp:ssp-android:1.1.0` (Scalable SP)

- **Architecture & Data**:
  - `androidx.lifecycle:lifecycle-livedata-ktx:2.3.1`
  - `androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1`
  - `androidx.room:room-runtime:2.6.1`
  - `androidx.navigation:navigation-fragment:2.7.0`
  - `androidx.navigation:navigation-ui:2.7.0`

- **Charts & Utilities**:
  - `com.github.PhilJay:MPAndroidChart:v3.1.0`

- **Testing**:
  - `junit:junit:4.13.2`
  - `androidx.test.ext:junit:1.1.3`
  - `androidx.test.espresso:espresso-core:3.4.0`

## Project Structure

### Source Layout
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/myapplication2/
│   │   │   ├── MainActivity.java           - Main entry point with navigation
│   │   │   ├── Splash.java                 - Launch screen
│   │   │   ├── Login.java                  - Authentication
│   │   │   ├── HorofActivity.java          - Arabic letters learning module
│   │   │   ├── SouarActivity.java          - Surahs learning module
│   │   │   ├── StatisticsActivity.java     - Progress tracking and charts
│   │   │   ├── SettingsActivity.java       - App configuration
│   │   │   ├── DBHelper.java               - SQLite/Room database management
│   │   │   └── StatsManager.java           - User statistics logic
│   │   └── res/
│   │       ├── layout/                     - Multi-variant layouts (land, sw, hdpi, etc.)
│   │       ├── raw/                        - Over 200 educational video assets (MP4)
│   │       └── values/                     - Styles, colors, and localized strings
├── build.gradle                            - Main module configuration
└── proguard-rules.pro                      - Code shrinking and obfuscation rules

keyGenerator/                               - Independent utility project
├── build.gradle                            - KeyGenerator configuration
└── src/                                    - Key generation source code
```

## Core Features

### 1. Educational Content
- **Horof (Letters)**: Learning Arabic letters with different vocalizations (Fatha, Dhama, Kasra, Sokoun) via interactive video content.
- **Souar (Chapters)**: Verse-by-verse learning of Quranic Surahs using specialized video assets.

### 2. Progress Tracking
- **Statistics**: Visual representation of learning progress using MPAndroidChart.
- **Database**: Persistent storage of user progress and app state.

### 3. Responsive UI
- **Device Support**: Extensive support for different screen sizes and orientations through specific layout qualifiers (`layout-land`, `layout-w936dp`, `layout-h720dp`, etc.).
- **RTL Support**: Full support for Right-to-Left languages.

## Permissions
- `READ_PHONE_STATE` / `READ_PRIVILEGED_PHONE_STATE`: For device identification/state.
- `POST_NOTIFICATIONS`: For user engagement.
- `AD_ID`: For analytics or advertising identifiers.

## Build Instructions

To build the project, use the provided Gradle wrapper:

- **Debug Build**: `./gradlew :app:assembleDebug`
- **Release Build**: `./gradlew :app:assembleRelease`

Note: The `keyGenerator` is a separate project and is not included in the main `:app` build by default.

## Themes & Styling
- Uses `MaterialComponents` as the base theme.
- Implements a dedicated `SplashTheme` for smooth app transitions.
- Utilizes `SDP` and `SSP` libraries to ensure UI consistency across varying screen densities.
