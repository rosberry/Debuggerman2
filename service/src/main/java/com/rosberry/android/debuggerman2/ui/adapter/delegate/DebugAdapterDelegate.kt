package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem

abstract class DebugAdapterDelegate(
    private val layoutId: Int
) {

    open val viewType: Int = layoutId

    abstract fun isFor(item: Any): Boolean

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebugItem)

    protected fun inflate(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}