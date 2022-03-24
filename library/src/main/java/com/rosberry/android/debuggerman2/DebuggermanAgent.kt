package com.rosberry.android.debuggerman2

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.extension.replace
import com.rosberry.android.debuggerman2.service.DebugAgentService
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class DebuggermanAgent<T : DebuggermanDialog> @PublishedApi internal constructor(
    private val activity: AppCompatActivity,
    private val dialogClass: KClass<T>
) : BroadcastReceiver(), LifecycleEventObserver {

    companion object {
        private const val ACTION_OPEN = "${BuildConfig.LIBRARY_PACKAGE_NAME}.action.open"
        private const val KEY_STACKTRACE = "${BuildConfig.LIBRARY_PACKAGE_NAME}.key.stacktrace"
        private const val TAG_DIALOG = "${BuildConfig.LIBRARY_PACKAGE_NAME}.tag.dialog"

        @PublishedApi
        internal var instance: DebuggermanAgent<out DebuggermanDialog>? = null

        private val notInitialized: String
            get() = "not initialized - call ${DebuggermanAgent::class.simpleName}::init first!"

        fun add(item: DebuggermanItem) = get()?.add(item)

        fun add(items: Collection<DebuggermanItem>) = get()?.add(items)

        fun remove(item: DebuggermanItem) = get()?.remove(item)

        fun remove(items: Collection<DebuggermanItem>) = get()?.remove(items)

        fun replace(target: DebuggermanItem, item: DebuggermanItem) = get()?.replace(target, item)

        inline fun <reified T : DebuggermanDialog> init(activity: AppCompatActivity) {
            if (instance != null) Log.w(this::class.simpleName, instance!!.alreadyInitialized)
            else instance = DebuggermanAgent(activity, T::class)
        }

        private fun get(): DebuggermanAgent<out DebuggermanDialog>? {
            if (instance == null) Log.w(DebuggermanAgent::class.simpleName, notInitialized)
            return instance
        }
    }

    @PublishedApi
    internal val alreadyInitialized: String
        get() = "already initialized for ${activity::class.simpleName} with ${dialogClass.simpleName} dialog!"

    private val dialog: DebuggermanDialog?
        get() = activity.supportFragmentManager.findFragmentByTag(TAG_DIALOG) as? DebuggermanDialog

    private val connection: Connection by lazy { Connection() }

    private val dynamicItems: MutableList<DebuggermanItem> by lazy { mutableListOf() }

    init {
        Thread.getDefaultUncaughtExceptionHandler().let { defaultExceptionHandler ->
            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                connection.service?.onAppCrashed(
                    activity.javaClass,
                    bundleOf(KEY_STACKTRACE to throwable.stackTraceToString())
                )
                defaultExceptionHandler?.uncaughtException(thread, throwable)
            }
        }
        activity.intent.getStringExtra(KEY_STACKTRACE)?.let { dynamicItems.add(DebuggermanItem.StackTrace(it)) }
        activity.lifecycle.addObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_OPEN) showDialog()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onLifecycleCreate()
            Lifecycle.Event.ON_DESTROY -> onLifecycleDestroy()
            else -> return
        }
    }

    private fun add(item: DebuggermanItem) {
        dynamicItems.add(item)
        dialog?.add(item)
    }

    private fun add(items: Collection<DebuggermanItem>) {
        dynamicItems.addAll(items)
        dialog?.add(items)
    }

    private fun remove(item: DebuggermanItem) {
        dynamicItems.remove(item)
        dialog?.remove(item)
    }

    private fun remove(items: Collection<DebuggermanItem>) {
        dynamicItems.removeAll(items)
        dialog?.remove(items)
    }

    private fun replace(target: DebuggermanItem, item: DebuggermanItem) {
        dynamicItems.replace(target, item)
        dialog?.replace(target, item)
    }

    private fun onLifecycleCreate() {
        activity.registerReceiver(this, IntentFilter(ACTION_OPEN))
        activity.bindService(
            Intent(activity, DebugAgentService::class.java),
            connection,
            Context.BIND_AUTO_CREATE or Context.BIND_ADJUST_WITH_ACTIVITY
        )
    }

    private fun onLifecycleDestroy() {
        activity.unregisterReceiver(this)
        activity.unbindService(connection)
        instance = null
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