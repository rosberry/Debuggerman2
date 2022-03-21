package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class ButtonDelegate : DebuggermanAdapterDelegate(R.layout.item_button) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder !is ViewHolder || item !is DebuggermanItem.Button) return

        holder.item = item
        holder.button.apply {
            setCompoundDrawablesRelativeWithIntrinsicBounds(item.icon, 0, 0, 0)
            text = item.label
        }
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val button: TextView = itemView.findViewById(R.id.button)

        lateinit var item: DebuggermanItem.Button

        init {
            button.setOnClickListener { item.listener.invoke() }
        }
    }
}