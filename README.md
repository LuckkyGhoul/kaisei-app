# KAISEI — Discipline Beyond Desire
継続は力なり — "Consistency is strength."

An offline, anime-themed self-discipline streak tracker for Android.
Every full day the user maintains their streak, a new original-dialogue
"chapter" narrated by a character inspired by a popular anime unlocks.

## IMPORTANT — read this first
This project was generated in a sandboxed dev environment that has **no
Android SDK and no network access to Google's Maven repositories**
(dl.google.com / maven.google.com — required for AGP, Compose, Room, etc.).
That means an APK could not be compiled here. What you have is the
**complete, ready-to-open Android Studio project** — open it, sync Gradle,
and build the APK on your own machine in a few minutes (steps below).

## What's implemented
- MVVM architecture: `data/` `database/` `repository/` `ui/` `viewmodel/` `utils/`
- Kotlin + Jetpack Compose + Material 3, fully offline, no login/backend/analytics
- Streak calculated from real timestamps (epoch millis, not a counter) — survives
  app kill, phone reboot, and multi-day gaps; timezone/DST-safe by design
- Live countdown to the next unlocked day (`utils/DateTimeUtils.kt`)
- Deterministic character scheduler (`data/CharacterScheduler.kt`) — Day N always
  shows the same character, never repeats on consecutive days, popular characters
  (Gojo, Dazai, Levi, Guts, Thorfinn, Itachi, Sung Jin-Woo…) appear more often
- 30-character modular pool (`data/CharacterPool.kt`) with 100% original
  motivational dialogue written to match each character's personality —
  no anime dialogue is copied. Swap `imageResName` for licensed/original art
  before any public release; the scheduler and rest of the app don't change.
- Cinematic unlock overlay (dark screen → "DAY N COMPLETE" → character reveal →
  dialogue → achievement toast → Continue)
- Chapters page (reread any unlocked day; future days stay locked)
- Achievement system: 11 seal-style titles from 始まり (Day 1) to 無双 (Day 365)
- XP/level system (100 XP/day, 250/achievement) with progress bar
- Journal with the 4 requested daily prompts, stored locally
- Settings: notifications/sound/haptics toggles, Reset Journey (keeps history),
  About/version
- Optional local notifications via WorkManager (morning/evening), no FCM
- Room for structured data (journeys, completed days, journal, achievements),
  DataStore for simple preferences/XP
- Onboarding + splash screens

## What's simplified vs. the full spec (be aware before you rely on this)
- Visual design uses Material 3 surfaces with a washi/gold/indigo palette, a
  bordered "scroll panel" component, and a Canvas-drawn seigaiha (wave)
  decorative strip on Home/Splash/Onboarding — but does NOT yet include
  ink-brush SVG/Lottie animations, asanoha pattern textures, or brush-stroke
  fonts. Those are the next visual layer to add.
- Character artwork is not bundled (see legal note below). `CharacterPortrait`
  looks up each character's `imageResName` at runtime and falls back to a
  clean initial medallion when no drawable exists — nothing crashes or shows
  a broken-image icon, it just shows the initial until you add real art.
- Only 30 characters / a rotation cycle of ~400 days-worth of assignments are
  defined, not 365 hand-written unique days — the scheduler reuses each
  character's dialogue pool (2–4 original lines each) on repeat appearances
  rather than requiring 365 entirely distinct chapters.
- Multi-day-gap unlocks currently surface only the most recent day's cinematic
  (all skipped days are still recorded and visible in Chapters/history).

## Legal note (per your prompt's own instructions)
Character *names* and *personality inspiration* are used for this personal
prototype; all dialogue is newly written, not copied from any source. No
copyrighted artwork is bundled. Replace names/art/anime references with
licensed or original equivalents before any public/commercial release —
the character data is isolated in one file (`CharacterPool.kt`) specifically
so that swap is easy.

## Build & install (Android Studio — do this on your own computer)
1. Install **Android Studio** (Koala or newer) if you don't have it.
2. Unzip this project, then **Open** the `KAISEI` folder in Android Studio.
3. Let Gradle sync (first sync downloads AGP 8.5.2 / Compose / Room — needs
   normal internet access to Google's Maven, which this sandbox didn't have).
4. Build > Build App Bundle(s)/APK(s) > Build APK(s).
   Output lands in `app/build/outputs/apk/debug/app-debug.apk`.
5. On your phone: transfer the APK (USB, Drive, email — your choice), open it,
   allow "install from this source" if Android asks, then Install and launch.

### Or from a terminal with the Android SDK installed
```
cd KAISEI
./gradlew assembleDebug
# APK at app/build/outputs/apk/debug/app-debug.apk
```
(No `gradlew` wrapper jar is included in this export to keep the download
small — running `gradle wrapper` once inside Android Studio's terminal, or
just using Android Studio's built-in Gradle, regenerates it automatically.)

## Project structure
```
app/src/main/java/com/kaisei/discipline/
├── data/            CharacterPool (dialogue), CharacterScheduler (deterministic rotation)
├── data/model/      Character, Achievement catalog, XP/Level system
├── database/        Room entities/DAOs/DB, DataStore settings
├── repository/      StreakRepository — single source of truth
├── viewmodel/       One ViewModel per screen + manual factory
├── ui/              theme/, home/, chapters/, achievements/, statistics/,
│                    journal/, settings/, onboarding/, splash/, nav/
└── utils/           DateTimeUtils, NotificationScheduler, ReminderWorker, BootReceiver
```
