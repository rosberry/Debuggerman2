package com.rosberry.android.debuggerman2

import android.view.inputmethod.EditorInfo
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog

class SampleDebugDialog : DebuggermanDialog() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    private val addButton = DebugItem.Button("Add dynamic controls", 0, ::addItems)
    private val dynamicButton = DebugItem.Button("Remove dynamic input", 0, ::removeItems)
    private val dynamicInput = DebugItem.Input("Dynamic input", null, EditorInfo.IME_ACTION_SEARCH) {}

    override fun setup() {
        add(
            DebugItem.Header("Example static header"),
            DebugItem.Toggle("Example static toggle") {},
            DebugItem.Button("Example static button") {},
            DebugItem.Input("Example static input") {},
            DebugItem.Selector("Example static selector", SelectorValue.values().toList()) {},
            addButton
        )
    }

    private fun addItems() {
        remove(addButton)
        add(dynamicButton, dynamicInput)
    }

    private fun removeItems() {
        remove(dynamicButton, dynamicInput)
        add(addButton)
    }
}