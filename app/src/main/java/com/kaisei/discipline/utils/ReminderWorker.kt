package com.kaisei.discipline.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaisei.discipline.database.SettingsDataStore
import kotlinx.coroutines.flow.first

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val settings = SettingsDataStore(applicationContext)
        val enabled = settings.notificationsOn.first()
        if (!enabled) return Result.success()

        val message = inputData.getString("message") ?: return Result.success()
        postNotification(message)
        return Result.success()
    }

    private fun postNotification(message: String) {
        val channelId = "kaisei_reminders"
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Journey Reminders", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("KAISEI")
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }
}
