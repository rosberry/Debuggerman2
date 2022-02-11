package com.rosberry.android.debuggerman2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class DebugAgentService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "debug_agent"
        private const val NOTIFICATION_ID = 1102
    }

    private val channelId: String get() = "$packageName.$NOTIFICATION_CHANNEL_ID"

    override fun onBind(intent: Intent?): IBinder = Binder()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        NotificationManagerCompat
            .from(applicationContext)
            .createNotificationChannel(
                NotificationChannel(channelId, "Debug dialog", NotificationManager.IMPORTANCE_HIGH)
            )
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, channelId)
            .setContentIntent(PendingIntent.getBroadcast(applicationContext, 0, Intent(DebugAgent.ACTION_OPEN), 0))
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText("Click to open debug dialog")
            .setContentTitle("Debug")
            .build()
    }

    inner class Binder : android.os.Binder() {

        val service get() = this@DebugAgentService
    }
}