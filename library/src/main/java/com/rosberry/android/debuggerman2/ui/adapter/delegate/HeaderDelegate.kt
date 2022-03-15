package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class HeaderDelegate : DebuggermanAdapterDelegate(R.layout.item_header) {

    override fun isFor(item: DebuggermanItem): Boolean = item is DebuggermanItem.Header

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder !is ViewHolder || item !is DebuggermanItem.Header) return

        holder.label.text = item.label
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val label: TextView = itemView.findViewById(R.id.label)
    }
}