package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class ButtonDelegate : DebuggermanAdapterDelegate(R.layout.item_button) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.Button) holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val button: TextView = itemView.findViewById(R.id.button)

        private lateinit var item: DebuggermanItem.Button

        init {
            button.setOnClickListener { item.listener.invoke() }
        }

        fun bind(item: DebuggermanItem.Button) {
            this.item = item
            button.setCompoundDrawablesRelativeWithIntrinsicBounds(item.icon, 0, 0, 0)
            button.text = item.label
        }
    }
}