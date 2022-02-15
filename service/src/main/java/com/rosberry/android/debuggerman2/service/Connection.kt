package com.rosberry.android.debuggerman2.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.rosberry.android.debuggerman2.service.DebugAgentService

internal class Connection : ServiceConnection {

    var service: DebugAgentService? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        this.service = (service as DebugAgentService.Binder).service
        this.service?.onServiceConnected()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        this.service = null
    }
}