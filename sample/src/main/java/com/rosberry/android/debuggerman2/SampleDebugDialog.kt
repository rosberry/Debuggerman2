package com.rosberry.android.debuggerman2

import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog

class SampleDebugDialog : DebuggermanDialog() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    override val items: MutableList<DebuggermanItem> = mutableListOf(
        DebuggermanItem.Toggle("Static toggle", "Static controls") {},
        DebuggermanItem.Button("Static button", "Static controls") {},
        DebuggermanItem.Button("Static loose button I") {},
        DebuggermanItem.Button("Static loose button II") {},
        DebuggermanItem.Button("Static loose button III") {},
        DebuggermanItem.Input("Static input", "Static controls") {},
        DebuggermanItem.Selector("Static selector", SelectorValue.values().toList(), "Static controls") {}
    )
}