package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebugAdapterDelegate

class DelegateManager(vararg delegates: DebugAdapterDelegate) {

    private val delegates: HashMap<Int, DebugAdapterDelegate> = hashMapOf()

    init {
        add(*delegates)
    }

    fun add(vararg delegates: DebugAdapterDelegate) {
        delegates.forEach { delegate -> this.delegates[delegate.viewType] = delegate }
    }

    fun remove(vararg delegates: DebugAdapterDelegate) {
        delegates.forEach { delegate -> this.delegates.remove(delegate.viewType) }
    }

    fun getItemViewType(item: DebugItem): Int {
        return delegates.values
            .find { delegate -> delegate.isFor(item) }
            ?.viewType
            ?: throw NullPointerException("No delegate registered for ${item::class.simpleName}")
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType]
            ?.createViewHolder(parent)
            ?: throw NullPointerException("No suitable delegate for $viewType view type")
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        delegates[holder.itemViewType]
            ?.onBindViewHolder(holder, item)
            ?: throw NullPointerException("No suitable delegate for ${holder::class.simpleName}")
    }
}