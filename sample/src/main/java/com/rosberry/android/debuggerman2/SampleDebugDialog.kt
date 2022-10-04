package com.rosberry.android.debuggerman2

import android.util.Log
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.DebuggermanDialog
import kotlin.math.roundToInt

class SampleDebugDialog : DebuggermanDialog() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    override val items: MutableList<DebuggermanItem> = mutableListOf(
        DebuggermanItem.Toggle("Static toggle", "Static controls") {},
        DebuggermanItem.Button("Static button", "Static controls") {},
        DebuggermanSubtitle("Items without group"),
        DebuggermanItem.Button("Static button without group") {},
        DebuggermanItem.Input("Static input", "Static controls") {},
        DebuggermanItem.Selector(SelectorValue.values().asList(), "Static selector", "Static controls") {},
        DebuggermanItem.Slider(0.5F, "Static controls", this::formatSliderValue) { value, _ ->
            Log.d("Slider", "Value: $value")
        }
    )

    private fun formatSliderValue(normalizedValue: Float): CharSequence =
        ((normalizedValue - 0.5F) * 400).roundToInt().toString()
}