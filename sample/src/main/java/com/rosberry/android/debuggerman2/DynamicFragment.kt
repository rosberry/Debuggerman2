package com.rosberry.android.debuggerman2

import android.os.Bundle
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class DynamicFragment : SampleFragment(R.layout.fragment_dynamic) {

    private enum class DynamicSelectorValue {
        HIDDEN, SHOWN
    }

    private val dynamicToggle by lazy { DebuggermanItem.Toggle("Dynamic toggle", "Dynamic controls") {} }

    private val items: List<DebuggermanItem> = listOf(
        DebuggermanItem.Button("Dynamic button", "Dynamic controls") {},
        DebuggermanItem.Selector(
            DynamicSelectorValue.values().asList(),
            "Dynamic toggle visibility",
            "Dynamic controls"
        ) { option -> setButtonVisibility(option) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebuggermanAgent.add(items)
    }

    override fun onDestroy() {
        DebuggermanAgent.remove(items)
        super.onDestroy()
    }

    private fun setButtonVisibility(option: DynamicSelectorValue) {
        when (option) {
            DynamicSelectorValue.HIDDEN -> DebuggermanAgent.remove(dynamicToggle)
            DynamicSelectorValue.SHOWN -> DebuggermanAgent.add(dynamicToggle)
        }
    }
}