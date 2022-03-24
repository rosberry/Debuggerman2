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
        DebuggermanAgent.add(items)
    }

    override fun onDestroy() {
        DebuggermanAgent.remove(items)
        super.onDestroy()
    }
}