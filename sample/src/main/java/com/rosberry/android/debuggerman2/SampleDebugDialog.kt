package com.rosberry.android.debuggerman2

import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog

class SampleDebugDialog : DebuggermanDialog() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    override val items: MutableList<DebuggermanItem> = mutableListOf(
        DebuggermanItem.Header("Example static header"),
        DebuggermanItem.Toggle("Example static toggle") {},
        DebuggermanItem.Button("Example static button") {},
        DebuggermanItem.Input("Example static input") {},
        DebuggermanItem.Selector("Example static selector", SelectorValue.values().toList()) {}
    )
}