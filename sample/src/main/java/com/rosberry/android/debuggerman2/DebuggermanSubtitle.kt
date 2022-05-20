package com.rosberry.android.debuggerman2

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebuggermanAdapterDelegate

data class DebuggermanSubtitle(
    val label: String,
    override val group: String? = null
) : DebuggermanItem(DebuggermanSubtitleDelegate::class)

class DebuggermanSubtitleDelegate : DebuggermanAdapterDelegate(R.layout.item_subtitle) {

    override fun createViewHolder(parent: ViewGroup) = object : RecyclerView.ViewHolder(inflate(parent)) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        holder.itemView.findViewById<TextView>(R.id.label)?.text = (item as? DebuggermanSubtitle)?.label
    }
}