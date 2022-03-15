package com.rosberry.android.debuggerman2

import android.view.inputmethod.EditorInfo
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog

class SampleDebugDialog : DebuggermanDialog() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    private val addButton = DebuggermanItem.Button("Add dynamic controls", 0, ::addItems)
    private val dynamicButton = DebuggermanItem.Button("Remove dynamic input", 0, ::removeItems)
    private val dynamicInput = DebuggermanItem.Input("Dynamic input", null, EditorInfo.IME_ACTION_SEARCH) {}

    override fun setup() {
        add(
            DebuggermanItem.Header("Example static header"),
            DebuggermanItem.Toggle("Example static toggle") {},
            DebuggermanItem.Button("Example static button") {},
            DebuggermanItem.Input("Example static input") {},
            DebuggermanItem.Selector("Example static selector", SelectorValue.values().toList()) {},
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