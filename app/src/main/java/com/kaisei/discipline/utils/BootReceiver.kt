package com.kaisei.discipline.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Re-schedules WorkManager reminders after a device reboot (WorkManager
 * periodic work otherwise survives reboot automatically on modern Android,
 * this is a defensive re-schedule for older OEM skins that clear it). */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            NotificationScheduler.scheduleDailyReminders(context)
        }
    }
}
