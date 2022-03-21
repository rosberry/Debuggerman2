package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class ToggleDelegate : DebuggermanAdapterDelegate(R.layout.item_toggle) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.Toggle) holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val toggle: SwitchCompat = itemView.findViewById(R.id.toggle)

        fun bind(item: DebuggermanItem.Toggle) {
            toggle.apply {
                setOnCheckedChangeListener(null)
                text = item.label
                isChecked = item.isChecked
                setOnCheckedChangeListener { _, b ->
                    item.isChecked = b
                    item.listener.invoke(b)
                }
            }
        }
    }
}