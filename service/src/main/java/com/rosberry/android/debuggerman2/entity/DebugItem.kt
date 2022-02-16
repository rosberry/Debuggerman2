package com.rosberry.android.debuggerman2.entity

import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ButtonDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebugAdapterDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.HeaderDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.InputDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.SelectorDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ToggleDelegate
import kotlin.reflect.KClass

sealed class DebugItem(
    val delegateClass: KClass<out DebugAdapterDelegate>
) {

    data class Header(
        val label: String
    ) : DebugItem(HeaderDelegate::class)

    data class Toggle(
        val label: String,
        var isChecked: Boolean = false,
        val listener: (Boolean) -> Unit
    ) : DebugItem(ToggleDelegate::class)

    data class Button(
        val label: String,
        @DrawableRes val icon: Int = 0,
        val listener: () -> Unit
    ) : DebugItem(ButtonDelegate::class)

    data class Input(
        val hint: String,
        var text: CharSequence? = null,
        val imeAction: Int = EditorInfo.IME_ACTION_DONE,
        val onTextChanged: ((CharSequence?) -> Unit)? = null,
        val onDone: (() -> Unit)? = null
    ) : DebugItem(InputDelegate::class)

    data class Selector(
        val label: String,
        val items: List<Any>,
        var selected: Any = items[0],
        val listener: (Any) -> Unit
    ) : DebugItem(SelectorDelegate::class)
}