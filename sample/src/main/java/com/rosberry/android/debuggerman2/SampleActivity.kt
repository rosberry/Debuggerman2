package com.rosberry.android.debuggerman2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SampleActivity : AppCompatActivity() {

    private lateinit var debugAgent: DebugAgent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        debugAgent = DebugAgent(this)
    }
}