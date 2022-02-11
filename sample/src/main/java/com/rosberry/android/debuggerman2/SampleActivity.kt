package com.rosberry.android.debuggerman2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugAgent.start(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DebugAgent.stop()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        DebugAgent.onNewIntent(this, intent)
    }
}