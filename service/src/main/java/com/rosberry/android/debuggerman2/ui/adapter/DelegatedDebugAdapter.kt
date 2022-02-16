package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem

class DelegatedDebugAdapter(
    private var items: List<DebugItem> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateManager = DelegateManager(items)

    fun setItems(items: List<DebugItem>) {
        this.items = items
        delegateManager.onAdapterItemsChanged(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = delegateManager.getItemViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBindViewHolder(holder, items[position])
    }
}