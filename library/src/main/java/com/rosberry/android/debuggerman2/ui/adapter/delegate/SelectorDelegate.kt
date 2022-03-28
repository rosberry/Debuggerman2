package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class SelectorDelegate : DebuggermanAdapterDelegate(R.layout.item_debuggerman_selector) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.Selector<out Any>) {
            (item as? DebuggermanItem.Selector<Any>)?.let(holder::bind)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val label: TextView = itemView.findViewById(R.id.label)
        private val value: TextView = itemView.findViewById(R.id.value)

        private lateinit var item: DebuggermanItem.Selector<Any>

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

        fun bind(item: DebuggermanItem.Selector<Any>) {
            this.item = item
            label.text = item.label
            value.text = item.selected.toString()
        }
    }
}