package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class InputDelegate : DebuggermanAdapterDelegate(R.layout.item_input) {

    override fun isFor(item: DebuggermanItem): Boolean = item is DebuggermanItem.Input

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder !is ViewHolder || item !is DebuggermanItem.Input) return

        holder.item = item
        holder.input.apply {
            imeOptions = item.imeAction
            hint = item.hint
            setText(item.text)
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val input: EditText = itemView.findViewById(R.id.input)

        lateinit var item: DebuggermanItem.Input

        init {
            input.doOnTextChanged { text, _, _, _ ->
                item.text = text
                item.onTextChanged?.invoke(text)
            }
            input.setOnEditorActionListener { input, actionId, _ ->
                if (actionId == item.imeAction) {
                    item.onDone?.invoke()
                    input.run {
                        clearFocus()
                        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                            .hideSoftInputFromWindow(windowToken, 0)
                    }
                }
                actionId == item.imeAction
            }
        }
    }
}