package com.rosberry.android.debuggerman2.entity

import androidx.annotation.DrawableRes

sealed class DebugItem {

    data class Header(
        val label: String
    ) : DebugItem()

    data class Toggle(
        val label: String,
        var isChecked: Boolean = false,
        val listener: (Boolean) -> Unit
    ) : DebugItem()

    data class Button(
        val label: String,
        @DrawableRes val icon: Int = 0,
        val listener: () -> Unit
    ) : DebugItem()

    data class Input(
        val hint: String,
        var text: CharSequence? = null,
        val onTextChanged: ((CharSequence?) -> Unit)? = null,
        val onDone: (() -> Unit)? = null
    ) : DebugItem()
}