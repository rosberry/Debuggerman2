package com.rosberry.android.debuggerman2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rosberry.android.debuggerman2.service.Connection
import com.rosberry.android.debuggerman2.service.DebugAgentService

class DebugAgent(
    private val activity: AppCompatActivity
) : BroadcastReceiver(), LifecycleEventObserver {

    companion object {
        const val ACTION_OPEN = "${BuildConfig.LIBRARY_PACKAGE_NAME}.action.open"

        const val KEY_STACKTRACE = "${BuildConfig.LIBRARY_PACKAGE_NAME}.key.stacktrace"
    }

    private val connection: Connection by lazy { Connection() }
    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    init {
        activity.lifecycle.addObserver(this)
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            connection.service?.onAppCrashed(activity.javaClass, throwable)
            defaultExceptionHandler?.uncaughtException(thread, throwable)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_OPEN -> showDialog()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> startAgent()
            Lifecycle.Event.ON_DESTROY -> stopAgent()
            else -> return
        }
    }

    private fun startAgent() {
        activity.registerReceiver(this, IntentFilter(ACTION_OPEN))
        activity.bindService(
            Intent(activity, DebugAgentService::class.java),
            connection,
            Context.BIND_AUTO_CREATE or Context.BIND_ADJUST_WITH_ACTIVITY
        )
    }

    private fun stopAgent() {
        activity.unregisterReceiver(this)
        activity.unbindService(connection)
    }

    private fun showDialog() {
        AlertDialog.Builder(activity)
            .setMessage("Notification clicked")
            .setTitle("Debug Agent")
            .show()
    }

}