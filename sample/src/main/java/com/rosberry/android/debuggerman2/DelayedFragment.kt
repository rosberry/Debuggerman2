package com.rosberry.android.debuggerman2

import android.os.Bundle
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class DelayedFragment : SampleFragment() {

    private val items = listOf(DebuggermanItem.Button("Close", "Delayed Fragment") {
        parentFragmentManager.popBackStack()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.addDebugItems(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.removeDebugItems(items)
    }
}