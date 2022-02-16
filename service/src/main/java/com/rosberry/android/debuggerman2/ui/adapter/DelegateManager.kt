package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebugAdapterDelegate
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class DelegateManager(items: List<DebugItem> = listOf()) {

    private val delegates: HashMap<Int, DebugAdapterDelegate> = hashMapOf()

    init {
        onAdapterItemsChanged(items)
    }

    fun onAdapterItemsChanged(items: List<DebugItem>) {
        items.mapTo(HashSet(), DebugItem::delegateClass).run {
            removeRedundantDelegates(this)
            addMissingDelegates(this)
        }
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

    private fun removeRedundantDelegates(kClasses: Set<KClass<*>>) {
        delegates.entries.asSequence()
            .filter { entry -> entry.value::class in kClasses }
            .forEach { entry -> delegates.remove(entry.key) }
    }

    private fun addMissingDelegates(kClasses: Set<KClass<out DebugAdapterDelegate>>) {
        kClasses.asSequence()
            .filterNot { kClass -> delegates.any { entry -> entry.value::class == kClass } }
            .map { kClass -> kClass.createInstance() }
            .forEach { delegate -> delegates[delegate.viewType] = delegate }
    }
}