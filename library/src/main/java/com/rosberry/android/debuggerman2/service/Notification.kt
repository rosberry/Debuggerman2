package com.rosberry.android.debuggerman2.service

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.rosberry.android.debuggerman2.R
import kotlin.random.Random

@SuppressLint("LaunchActivityFromNotification")
object Notification {

    private val actionFlags: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE
        else 0

    fun connected(
        context: Context,
        channelId: String,
        openAction: String
    ): Notification {
        val pendingIntent = PendingIntent.getBroadcast(context, 1, Intent(openAction), actionFlags)

        return NotificationCompat.Builder(context, channelId)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setSmallIcon(R.drawable.ic_debuggerman_droid)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle("Application is running")
            .setContentText("Tap the notification to open debug menu")
            .setContentIntent(pendingIntent)
            .build()
    }

    fun crashed(
        context: Context,
        channelId: String,
        activityClass: Class<Activity>,
        extras: Bundle
    ): Notification {
        val intent = Intent(context, activityClass).apply { putExtras(extras) }
        val pendingIntent = PendingIntent.getActivity(context, Random.nextInt(), intent, actionFlags)

        return NotificationCompat.Builder(context, channelId)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setSmallIcon(R.drawable.ic_debuggerman_bug)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentTitle("Application crashed")
            .setContentText("Tap the notification to restart")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
}