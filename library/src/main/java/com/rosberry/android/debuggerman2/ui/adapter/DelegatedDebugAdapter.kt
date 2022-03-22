package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.entity.DebuggermanItem.Title

class DelegatedDebugAdapter(
    private var items: List<DebuggermanItem> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateManager by lazy { DelegateManager(items) }
    private val diffCallback = DiffCallback()

    init {
        if (items.isNotEmpty()) items = items.insertGroupHeaders()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = delegateManager.getItemViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBindViewHolder(holder, items[position])
    }

    fun setItems(items: List<DebuggermanItem>) {
        val newItems = items.insertGroupHeaders()
        val diffResult = diffCallback.calculateDiff(this.items, newItems)

        delegateManager.onAdapterItemsChanged(newItems)
        this@DelegatedDebugAdapter.items = newItems
        diffResult.dispatchUpdatesTo(this@DelegatedDebugAdapter)
    }

    private fun List<DebuggermanItem>.insertGroupHeaders(): List<DebuggermanItem> {
        val groups = groupBy(DebuggermanItem::group)
        return mutableListOf<DebuggermanItem>().apply {
            groups.keys
                .sortedWith(nullsLast { _, _ -> 0 })
                .forEach { group ->
                    if (group != null) this.add(Title(group))
                    groups[group]?.let(this::addAll)
                }
        }
    }

    private class DiffCallback : DiffUtil.Callback() {

        private lateinit var old: List<DebuggermanItem>
        private lateinit var new: List<DebuggermanItem>

        fun calculateDiff(old: List<DebuggermanItem>, new: List<DebuggermanItem>): DiffUtil.DiffResult {
            this.old = old
            this.new = new

            return DiffUtil.calculateDiff(this)
        }

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return old[oldPosition] == new[newPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return old[oldPosition] == new[newPosition]
        }
    }
}