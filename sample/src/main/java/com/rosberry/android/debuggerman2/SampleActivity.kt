package com.rosberry.android.debuggerman2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DebuggermanAgent.init<SampleDebugDialog>(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_app, MenuFragment())
            .commit()
    }
}