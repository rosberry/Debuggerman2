package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class TitleDelegate : DebuggermanAdapterDelegate(R.layout.item_title) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.Title) holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val label: TextView = itemView.findViewById(R.id.label)

        fun bind(item: DebuggermanItem.Title) {
            label.text = item.label
        }
    }
}