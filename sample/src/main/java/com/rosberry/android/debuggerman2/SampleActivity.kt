package com.rosberry.android.debuggerman2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SampleActivity : AppCompatActivity() {

    private lateinit var debugAgent: DebugAgent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        debugAgent = DebugAgent(this)

        findViewById<View>(R.id.btn_crash).setOnClickListener { throw Exception() }
    }
}