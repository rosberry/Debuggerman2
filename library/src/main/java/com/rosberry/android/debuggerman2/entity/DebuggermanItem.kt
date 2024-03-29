package com.rosberry.android.debuggerman2.entity

import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ButtonDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebuggermanAdapterDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.InputDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.SelectorDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.SliderDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.StackTraceDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.TitleDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ToggleDelegate
import kotlin.reflect.KClass

open class DebuggermanItem(
    val delegateClass: KClass<out DebuggermanAdapterDelegate>,
    open val group: String? = null
) {

    data class Title(
        val label: String
    ) : DebuggermanItem(TitleDelegate::class)

    data class Toggle(
        val label: String,
        override val group: String? = null,
        var isChecked: Boolean = false,
        val listener: (Boolean) -> Unit
    ) : DebuggermanItem(ToggleDelegate::class)

    data class Button(
        val label: String,
        override val group: String? = null,
        @DrawableRes val icon: Int = 0,
        val listener: () -> Unit
    ) : DebuggermanItem(ButtonDelegate::class)

    data class Input(
        val hint: String,
        override val group: String? = null,
        var text: CharSequence? = null,
        val imeAction: Int = EditorInfo.IME_ACTION_DONE,
        val onTextChanged: ((CharSequence?) -> Unit)? = null,
        val onDone: ((CharSequence?) -> Unit)? = null
    ) : DebuggermanItem(InputDelegate::class)

    data class Selector<T : Any>(
        val items: List<T>,
        val label: String,
        override val group: String? = null,
        var selected: T = items[0],
        val listener: (T) -> Unit
    ) : DebuggermanItem(SelectorDelegate::class)

    data class StackTrace(
        val stackTrace: String,
        override val group: String?,
        var isExpanded: Boolean = false
    ) : DebuggermanItem(StackTraceDelegate::class)

    data class Slider(
        var value: Float = 0F,
        override val group: String?,
        val formatter: (Float) -> CharSequence,
        val listener: (Float, Boolean) -> Unit
    ) : DebuggermanItem(SliderDelegate::class)
}