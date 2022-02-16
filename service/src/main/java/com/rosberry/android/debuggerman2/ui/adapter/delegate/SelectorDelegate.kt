package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem

class SelectorDelegate : DebugAdapterDelegate(R.layout.item_selector) {

    override fun isFor(item: DebugItem): Boolean = item is DebugItem.Selector

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        if (holder !is ViewHolder || item !is DebugItem.Selector) return

        holder.item = item
        holder.label.text = item.label
        holder.value.text = item.selected.toString()
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val label: TextView = itemView.findViewById(R.id.label)
        val value: TextView = itemView.findViewById(R.id.value)

        lateinit var item: DebugItem.Selector

        init {
            itemView.setOnClickListener {
                val index = item.items.indexOf(item.selected)
                val next = if (index < item.items.size - 1) index + 1 else 0
                val value = item.items[next]

                this.value.text = value.toString()

                item.selected = value
                item.listener.invoke(value)
            }
        }
    }
}