package com.rosberry.android.debuggerman2

import android.os.Bundle
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class DynamicFragment : SampleFragment(R.layout.fragment_dynamic) {

    private val items: List<DebuggermanItem> = listOf(
        DebuggermanItem.Toggle("Dynamic toggle", "Dynamic controls") {},
        DebuggermanItem.Button("Dynamic button", "Dynamic controls") {},
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.addDebugItems(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.removeDebugItems(items)
    }
}