package com.kaisei.discipline.utils

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * Schedules optional local notifications using WorkManager (no FCM, no
 * network, fully offline). Actual notification posting logic lives in
 * ReminderWorker; this object just (re)schedules the periodic work.
 */
object NotificationScheduler {

    private const val MORNING_WORK = "kaisei_morning_reminder"
    private const val EVENING_WORK = "kaisei_evening_reminder"

    fun scheduleDailyReminders(context: Context) {
        val morningRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInputData(workDataOf("message" to "Your journey continues. Stay disciplined."))
            .setInitialDelay(computeInitialDelayMinutes(hour = 9), TimeUnit.MINUTES)
            .build()

        val eveningRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInputData(workDataOf("message" to "One more day. The next chapter awaits."))
            .setInitialDelay(computeInitialDelayMinutes(hour = 21), TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            MORNING_WORK, ExistingPeriodicWorkPolicy.KEEP, morningRequest
        )
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            EVENING_WORK, ExistingPeriodicWorkPolicy.KEEP, eveningRequest
        )
    }

    fun cancelAll(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(MORNING_WORK)
        WorkManager.getInstance(context).cancelUniqueWork(EVENING_WORK)
    }

    private fun computeInitialDelayMinutes(hour: Int): Long {
        val now = java.time.LocalDateTime.now()
        var target = now.withHour(hour).withMinute(0).withSecond(0)
        if (target.isBefore(now)) target = target.plusDays(1)
        return java.time.Duration.between(now, target).toMinutes()
    }
}
