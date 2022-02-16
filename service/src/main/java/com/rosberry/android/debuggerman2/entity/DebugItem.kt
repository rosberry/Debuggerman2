package com.rosberry.android.debuggerman2.entity

import android.view.inputmethod.EditorInfo
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
        val imeAction: Int = EditorInfo.IME_ACTION_DONE,
        val onTextChanged: ((CharSequence?) -> Unit)? = null,
        val onDone: (() -> Unit)? = null
    ) : DebugItem()

    data class Selector(
        val label: String,
        val items: List<Any>,
        var selected: Any = items[0],
        val listener: (Any) -> Unit
    ) : DebugItem()
}