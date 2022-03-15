package com.rosberry.android.debuggerman2.entity

import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ButtonDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebuggermanAdapterDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.HeaderDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.InputDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.SelectorDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ToggleDelegate
import kotlin.reflect.KClass

sealed class DebuggermanItem(
    val delegateClass: KClass<out DebuggermanAdapterDelegate>
) {

    data class Header(
        val label: String
    ) : DebuggermanItem(HeaderDelegate::class)

    data class Toggle(
        val label: String,
        var isChecked: Boolean = false,
        val listener: (Boolean) -> Unit
    ) : DebuggermanItem(ToggleDelegate::class)

    data class Button(
        val label: String,
        @DrawableRes val icon: Int = 0,
        val listener: () -> Unit
    ) : DebuggermanItem(ButtonDelegate::class)

    data class Input(
        val hint: String,
        var text: CharSequence? = null,
        val imeAction: Int = EditorInfo.IME_ACTION_DONE,
        val onTextChanged: ((CharSequence?) -> Unit)? = null,
        val onDone: (() -> Unit)? = null
    ) : DebuggermanItem(InputDelegate::class)

    data class Selector(
        val label: String,
        val items: List<Any>,
        var selected: Any = items[0],
        val listener: (Any) -> Unit
    ) : DebuggermanItem(SelectorDelegate::class)
}