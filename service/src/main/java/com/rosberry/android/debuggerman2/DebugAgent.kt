package com.rosberry.android.debuggerman2

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf

class DebugAgent : Service() {

    companion object {
        private const val ACTION_OPEN = "${BuildConfig.LIBRARY_PACKAGE_NAME}.debug.open"
        private const val ACTION_START = "${BuildConfig.LIBRARY_PACKAGE_NAME}.agent.start"

        private const val ARG_ACTIVITY_CLASS = "arg_activity_class"

        private const val CHANNEL_ID = "debug_agent"
        private const val NOTIFICATION_ID = 1102

        private var applicationContext: Context? = null

        fun start(activity: Activity) {
            applicationContext = activity.applicationContext
            applicationContext?.startService(Intent(applicationContext, DebugAgent::class.java).apply {
                action = ACTION_START
                putExtras(bundleOf(ARG_ACTIVITY_CLASS to activity::class.java))
            })
        }

        fun stop() {
            applicationContext?.run { stopService(Intent(this, DebugAgent::class.java)) }
            applicationContext = null
        }

        fun onNewIntent(context: Context, intent: Intent?) {
            if (intent?.action == ACTION_OPEN)
                AlertDialog.Builder(context)
                    .setMessage("")
                    .create()
                    .show()
        }
    }

    private val channelId: String get() = "$packageName.$CHANNEL_ID"
    private val launchIntent: Intent?
        get() = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            action = ACTION_OPEN
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    private val contentIntentFlags: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else PendingIntent.FLAG_UPDATE_CURRENT

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_START) showNotification()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel()

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentIntent(PendingIntent.getActivity(applicationContext, 0, launchIntent, contentIntentFlags))
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText("Click to open debug dialog")
            .setContentTitle("Debug")
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        NotificationManagerCompat
            .from(applicationContext)
            .createNotificationChannel(
                NotificationChannel(channelId, "Debug dialog", NotificationManager.IMPORTANCE_HIGH)
            )
    }
}