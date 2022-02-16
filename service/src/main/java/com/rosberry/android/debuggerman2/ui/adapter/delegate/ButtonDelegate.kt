package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem

class ButtonDelegate : DebugAdapterDelegate(R.layout.item_button) {

    override fun isFor(item: DebugItem): Boolean = item is DebugItem.Button

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        if (holder !is ViewHolder || item !is DebugItem.Button) return

        holder.item = item
        holder.button.apply {
            setCompoundDrawablesRelativeWithIntrinsicBounds(item.icon, 0, 0, 0)
            text = item.label
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val button: TextView = itemView.findViewById(R.id.button)

        lateinit var item: DebugItem.Button

        init {
            button.setOnClickListener { item.listener.invoke() }
        }
    }
}