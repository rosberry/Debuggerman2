package com.rosberry.android.debuggerman2

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class DebugAgent(
    private val activity: AppCompatActivity
) : BroadcastReceiver(), LifecycleEventObserver {

    companion object {
        const val ACTION_OPEN = "${BuildConfig.LIBRARY_PACKAGE_NAME}.debug.open"
    }

    private val connection: Connection by lazy { Connection() }

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        AlertDialog.Builder(activity)
            .setMessage("Notification clicked")
            .setTitle("Debug Agent")
            .show()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> startService()
            Lifecycle.Event.ON_DESTROY -> stopService()
            else -> return
        }
    }

    private fun startService() {
        activity.registerReceiver(this, IntentFilter(ACTION_OPEN))
        activity.bindService(
            Intent(activity, DebugAgentService::class.java),
            connection,
            Context.BIND_AUTO_CREATE or Context.BIND_ADJUST_WITH_ACTIVITY
        )
    }

    private fun stopService() {
        activity.unregisterReceiver(this)
        activity.unbindService(connection)
    }

    private inner class Connection : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
}