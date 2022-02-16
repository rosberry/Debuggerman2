package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem

class DelegatedDebugAdapter(
    private var items: List<DebugItem> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val delegates = DelegateManager()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = delegates.getItemViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates.onBindViewHolder(holder, items[position])
    }
}