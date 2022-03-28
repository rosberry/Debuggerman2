package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class StackTraceDelegate : DebuggermanAdapterDelegate(R.layout.item_debuggerman_stacktrace) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.StackTrace) holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val btnCopy: View = itemView.findViewById(R.id.btn_copy)
        private val textStackTrace: TextView = itemView.findViewById(R.id.text_stacktrace)
        private val textTitle: TextView = itemView.findViewById(R.id.text_title)

        private lateinit var item: DebuggermanItem.StackTrace

        init {
            btnCopy.setOnClickListener { button ->
                (button.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                    ClipData.newPlainText(
                        button.resources.getString(R.string.debuggerman_stacktrace_title),
                        item.stackTrace
                    )
                )
                Toast.makeText(
                    button.context,
                    button.resources.getString(R.string.debuggerman_stacktrace_copied),
                    Toast.LENGTH_SHORT
                ).show()
            }
            textTitle.setOnClickListener {
                item.isExpanded = !item.isExpanded
                update(item.isExpanded)
            }
        }

        fun bind(item: DebuggermanItem.StackTrace) {
            this.item = item
            textStackTrace.text = item.stackTrace
            update(item.isExpanded)
        }

        private fun update(isExpanded: Boolean) {
            textStackTrace.updateLayoutParams { height = if (isExpanded) ViewGroup.LayoutParams.WRAP_CONTENT else 0 }
            textTitle.text = itemView.resources.getString(
                if (isExpanded) R.string.debuggerman_stacktrace_collapse
                else R.string.debuggerman_stacktrace_expand
            )
        }
    }
}