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

internal const val KEY_STACKTRACE = "${BuildConfig.LIBRARY_PACKAGE_NAME}.stacktrace"

class DebuggermanAgent<T : DebuggermanDialog> @PublishedApi internal constructor(
    private val activity: AppCompatActivity,
    private val dialogClass: KClass<T>
) : BroadcastReceiver(), LifecycleEventObserver {

    companion object {
        private const val TAG_DIALOG = "${BuildConfig.LIBRARY_PACKAGE_NAME}.dialog"

        @PublishedApi
        internal var instance: DebuggermanAgent<out DebuggermanDialog>? = null

        private val notInitialized: String
            get() = "not initialized - call ${DebuggermanAgent::class.simpleName}::init first!"

        /**
         * Add provided item to dynamic items collection.
         */
        fun add(item: DebuggermanItem) = get()?.add(item)

        /**
         * Add provided items to dynamic items collection.
         */
        fun add(items: Collection<DebuggermanItem>) = get()?.add(items)

        /**
         * Remove provided item from dynamic items collection.
         */
        fun remove(item: DebuggermanItem) = get()?.remove(item)

        /**
         * Remove provided items from dynamic items collection.
         */
        fun remove(items: Collection<DebuggermanItem>) = get()?.remove(items)

        /**
         * Replace `target` with `item` in dynamic items collection.
         * If the dialog is visible, changes will be applied immediately.
         */
        fun replace(target: DebuggermanItem, item: DebuggermanItem) = get()?.replace(target, item)

        /**
         * Force refresh provided [DebuggermanItem]'s view.
         *
         * Has no effect if dialog is not visible or [DebuggermanDialog.items] doesn't contain provided item.
         */
        fun update(item: DebuggermanItem) = get()?.update(item)

        /**
         * Initialize new [DebuggermanAgent] instance that wraps default exception handler to intercept stacktrace
         * in case of application crash and observes provided activity lifecycle to handle service accordingly.
         *
         * If DebuggermanAgent instance already exists send WARN log message with details.
         */
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

    private val actionOpen: String by lazy { "${activity.packageName}.action.open" }

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
        activity.lifecycle.addObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == actionOpen) showDialog()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onLifecycleCreate()
            Lifecycle.Event.ON_RESUME -> connection.onResume()
            Lifecycle.Event.ON_STOP -> connection.onStop()
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

    private fun update(item: DebuggermanItem) {
        dialog?.update(item)
    }

    private fun onLifecycleCreate() {
        activity.registerReceiver(this, IntentFilter(actionOpen))
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
            if (!isDestroyed && !isStateSaved && findFragmentByTag(TAG_DIALOG) == null)
                dialogClass
                    .createInstance()
                    .applyOnCreate { add(dynamicItems) }
                    .show(this, TAG_DIALOG)
        }
    }

    private inner class Connection : ServiceConnection {

        var service: DebugAgentService? = null

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            this.service = (service as DebugAgentService.Binder).service
            this.service?.onConnected(actionOpen)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this.service = null
        }

        fun onResume() {
            this.service?.onAppForeground(actionOpen)
        }

        fun onStop() {
            this.service?.onAppBackground(activity.javaClass)
        }
    }
}