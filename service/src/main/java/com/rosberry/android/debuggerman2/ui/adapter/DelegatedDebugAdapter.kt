package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem

class DelegatedDebugAdapter(
    private var items: List<DebugItem> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateManager = DelegateManager(items)
    private val diffCallback = DiffCallback()

    fun setItems(items: List<DebugItem>) {
        val diffResult = diffCallback.calculateDiff(this.items, items)

        delegateManager.onAdapterItemsChanged(items)
        this@DelegatedDebugAdapter.items = items
        diffResult.dispatchUpdatesTo(this@DelegatedDebugAdapter)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = delegateManager.getItemViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBindViewHolder(holder, items[position])
    }

    private class DiffCallback : DiffUtil.Callback() {

        private lateinit var old: List<DebugItem>
        private lateinit var new: List<DebugItem>

        fun calculateDiff(old: List<DebugItem>, new: List<DebugItem>): DiffUtil.DiffResult {
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