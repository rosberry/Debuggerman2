package com.rosberry.android.debuggerman2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class SampleActivity : AppCompatActivity() {

    private lateinit var debugAgent: DebuggermanAgent<SampleDebugDialog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debugAgent = DebuggermanAgent(this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_app, MenuFragment())
            .commit()
    }

    fun addDebugItems(items: Collection<DebuggermanItem>) {
        debugAgent.add(items)
    }

    fun removeDebugItems(items: Collection<DebuggermanItem>) {
        debugAgent.remove(items)
    }
}