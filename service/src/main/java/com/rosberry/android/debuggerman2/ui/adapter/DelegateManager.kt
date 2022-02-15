package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.ui.adapter.delegate.AdapterDelegate

class DelegateManager<T1 : Any, T2 : AdapterDelegate<T1>>(vararg delegates: T2) {

    private val delegates: HashMap<Int, T2> = hashMapOf()

    init {
        delegates.forEach { delegate -> this.delegates[delegate.viewType] = delegate }
    }

    fun getItemViewType(item: Any): Int {
        return delegates.values
            .find { delegate -> delegate.isFor(item) }
            ?.viewType
            ?: throw NullPointerException("No delegate registered for ${item::class.simpleName}")
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType]!!.createViewHolder(parent)
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: T1) {
        delegates[holder.itemViewType]!!.onBindViewHolder(holder, item)
    }
}