package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.entity.HeaderItem

class HeaderDelegate : DebugAdapterDelegate(R.layout.item_header) {

    override fun isFor(item: Any): Boolean = item is HeaderItem

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        if (holder !is ViewHolder || item !is HeaderItem) return

        holder.itemView
            .findViewById<TextView>(R.id.text_header)
            .text = item.text
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}