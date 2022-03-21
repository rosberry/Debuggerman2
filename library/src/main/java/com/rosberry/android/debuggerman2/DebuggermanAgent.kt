package com.rosberry.android.debuggerman2

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.service.DebugAgentService
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

inline fun <reified T : DebuggermanDialog> DebuggermanAgent(activity: AppCompatActivity): DebuggermanAgent<T> {
    return DebuggermanAgent(activity, T::class)
}

class DebuggermanAgent<T : DebuggermanDialog> @PublishedApi internal constructor(
    private val activity: AppCompatActivity,
    private val dialogClass: KClass<T>
) : BroadcastReceiver(), LifecycleEventObserver {

    companion object {
        private const val ACTION_OPEN = "${BuildConfig.LIBRARY_PACKAGE_NAME}.action.open"
        private const val KEY_STACKTRACE = "${BuildConfig.LIBRARY_PACKAGE_NAME}.key.stacktrace"
        private const val TAG_DIALOG = "${BuildConfig.LIBRARY_PACKAGE_NAME}.tag.dialog"
    }

    private val connection: Connection by lazy { Connection() }

    private val dynamicItems: MutableList<DebuggermanItem> by lazy { mutableListOf() }

    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    init {
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            connection.service?.onAppCrashed(
                activity.javaClass,
                bundleOf(KEY_STACKTRACE to throwable.stackTraceToString())
            )
            defaultExceptionHandler?.uncaughtException(thread, throwable)
        }
        activity.intent.getStringExtra(KEY_STACKTRACE)?.let { dynamicItems.add(DebuggermanItem.StackTrace(it)) }
        activity.lifecycle.addObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_OPEN) showDialog()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> startAgent()
            Lifecycle.Event.ON_DESTROY -> stopAgent()
            else -> return
        }
    }

    fun add(items: Collection<DebuggermanItem>) {
        this.dynamicItems.addAll(items)
        (activity.supportFragmentManager.findFragmentByTag(TAG_DIALOG) as? DebuggermanDialog)?.add(items)
    }

    fun remove(items: Collection<DebuggermanItem>) {
        this.dynamicItems.removeAll(items)
        (activity.supportFragmentManager.findFragmentByTag(TAG_DIALOG) as? DebuggermanDialog)?.remove(items)
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
        activity.supportFragmentManager.run {
            if (findFragmentByTag(TAG_DIALOG) == null)
                dialogClass
                    .createInstance()
                    .apply { add(dynamicItems) }
                    .show(this, TAG_DIALOG)
        }
    }

    internal class Connection : ServiceConnection {

        var service: DebugAgentService? = null

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            this.service = (service as DebugAgentService.Binder).service
            this.service?.onServiceConnected(ACTION_OPEN)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this.service = null
        }
    }
}