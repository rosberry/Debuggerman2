package com.rosberry.android.debuggerman2.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.ui.adapter.delegate.DebuggermanAdapterDelegate
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class DelegateManager(items: List<DebuggermanItem> = listOf()) {

    private val delegates: HashMap<Int, DebuggermanAdapterDelegate> = hashMapOf()

    init {
        onAdapterItemsChanged(items)
    }

    fun onAdapterItemsChanged(items: List<DebuggermanItem>) {
        items.mapTo(HashSet(), DebuggermanItem::delegateClass).run {
            removeRedundantDelegates(this)
            addMissingDelegates(this)
        }
    }

    fun getItemViewType(item: DebuggermanItem): Int {
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

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        delegates[holder.itemViewType]
            ?.onBindViewHolder(holder, item)
            ?: throw NullPointerException("No suitable delegate for ${holder::class.simpleName}")
    }

    private fun removeRedundantDelegates(kClasses: Set<KClass<*>>) {
        delegates.entries.iterator().run { while (hasNext()) if (next().value::class in kClasses) remove() }
    }

    private fun addMissingDelegates(kClasses: Set<KClass<out DebuggermanAdapterDelegate>>) {
        kClasses.asSequence()
            .filterNot { kClass -> delegates.any { entry -> entry.value::class == kClass } }
            .map { kClass -> kClass.createInstance() }
            .forEach { delegate -> delegates[delegate.viewType] = delegate }
    }
}