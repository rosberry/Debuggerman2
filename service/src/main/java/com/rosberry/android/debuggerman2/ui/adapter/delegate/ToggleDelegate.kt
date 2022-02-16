package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem

class ToggleDelegate : DebugAdapterDelegate(R.layout.item_toggle) {

    override fun isFor(item: DebugItem): Boolean = item is DebugItem.Toggle

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem) {
        if (holder !is ViewHolder || item !is DebugItem.Toggle) return

        holder.toggle.apply {
            setOnCheckedChangeListener(null)
            text = item.label
            isChecked = item.isChecked
            setOnCheckedChangeListener { _, b ->
                item.isChecked = b
                item.listener.invoke(b)
            }
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val toggle: SwitchCompat = itemView.findViewById(R.id.toggle)
    }
}