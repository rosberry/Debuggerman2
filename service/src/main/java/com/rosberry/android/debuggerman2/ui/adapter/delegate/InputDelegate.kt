package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem

class InputDelegate : DebugAdapterDelegate(R.layout.item_input) {

    override fun isFor(item: DebugItem): Boolean = item is DebugItem.Input

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        if (holder !is ViewHolder || item !is DebugItem.Input) return

        holder.item = item
        holder.input.apply {
            hint = item.hint
            setText(item.text)
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val input: EditText = itemView.findViewById(R.id.input)

        lateinit var item: DebugItem.Input

        init {
            input.doOnTextChanged { text, _, _, _ ->
                item.text = text
                item.onTextChanged?.invoke(text)
            }
            input.setOnEditorActionListener { input, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    item.onDone?.invoke()
                    input.clearFocus()
                    input.context.getSystemService(InputMethodManager::class.java)
                        .hideSoftInputFromWindow(input.windowToken, 0)
                }
                actionId == EditorInfo.IME_ACTION_DONE
            }
        }
    }
}