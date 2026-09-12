package com.kaisei.discipline.utils

/**
 * All streak math is done with epoch-millisecond arithmetic (java.time Instant
 * under the hood, via System.currentTimeMillis()) rather than wall-clock
 * LocalDateTime. That means:
 *  - Timezone changes don't corrupt the streak (epoch millis are absolute).
 *  - DST transitions don't skip or duplicate a day.
 *  - Leap years/months need no special-casing — we just count 24h periods.
 *
 * A "day" = one full 24-hour period since the journey's start timestamp.
 * This is intentionally NOT tied to local midnight, so it behaves identically
 * no matter what time zone the phone is in when the day rolls over.
 */
object DateTimeUtils {

    const val ONE_DAY_MILLIS: Long = 24L * 60L * 60L * 1000L

    /** Number of FULL 24h periods that have elapsed since [startMillis]. This is the current streak. */
    fun completedDayCount(startMillis: Long, nowMillis: Long = System.currentTimeMillis()): Int {
        if (nowMillis <= startMillis) return 0
        return ((nowMillis - startMillis) / ONE_DAY_MILLIS).toInt()
    }

    /** Epoch millis at which the NEXT day (completedDayCount + 1) will unlock. */
    fun nextUnlockEpochMillis(startMillis: Long, completedDays: Int): Long =
        startMillis + (completedDays + 1).toLong() * ONE_DAY_MILLIS

    /** Milliseconds remaining until the next unlock. Never negative. */
    fun remainingMillis(nextUnlockEpochMillis: Long, nowMillis: Long = System.currentTimeMillis()): Long =
        (nextUnlockEpochMillis - nowMillis).coerceAtLeast(0)

    data class Countdown(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)

    fun toCountdown(remainingMillis: Long): Countdown {
        var rem = remainingMillis
        val days = rem / (24 * 60 * 60 * 1000); rem %= (24 * 60 * 60 * 1000)
        val hours = rem / (60 * 60 * 1000); rem %= (60 * 60 * 1000)
        val minutes = rem / (60 * 1000); rem %= (60 * 1000)
        val seconds = rem / 1000
        return Countdown(days, hours, minutes, seconds)
    }

    fun formatDate(epochMillis: Long): String {
        val instant = java.time.Instant.ofEpochMilli(epochMillis)
        val ldt = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
        val formatter = java.time.format.DateTimeFormatter.ofPattern("d MMM yyyy")
        return ldt.format(formatter)
    }
}
