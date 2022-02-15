package com.rosberry.android.debuggerman2.service

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class DebugAgentService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "debug_agent"
        private const val NOTIFICATION_CRASH_ID = 1101
        private const val NOTIFICATION_ID = 1102
    }

    private val channelId: String get() = "$packageName.$NOTIFICATION_CHANNEL_ID"
    private val notificationManager get() = NotificationManagerCompat.from(applicationContext)

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    override fun onBind(intent: Intent?): IBinder = Binder()

    fun onServiceConnected(action: String) {
        notificationManager.cancel(NOTIFICATION_CRASH_ID)
        startForeground(NOTIFICATION_ID, Notification.connected(applicationContext, channelId, action))
    }

    fun onAppCrashed(activityClass: Class<Activity>, extras: Bundle) {
        notificationManager.cancel(NOTIFICATION_ID)
        notificationManager.notify(
            NOTIFICATION_CRASH_ID,
            Notification.crashed(applicationContext, channelId, activityClass, extras)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        notificationManager.createNotificationChannel(
            NotificationChannel(channelId, "Debug dialog", NotificationManager.IMPORTANCE_HIGH)
        )
    }

    inner class Binder : android.os.Binder() {

        val service get() = this@DebugAgentService
    }
}