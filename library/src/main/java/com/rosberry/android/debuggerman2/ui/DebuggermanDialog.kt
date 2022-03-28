package com.rosberry.android.debuggerman2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem
import com.rosberry.android.debuggerman2.extension.replace
import com.rosberry.android.debuggerman2.ui.adapter.DelegatedDebugAdapter

open class DebuggermanDialog : BottomSheetDialogFragment() {

    protected open val items: MutableList<DebuggermanItem> = mutableListOf()

    private val debugAdapter: DelegatedDebugAdapter by lazy { DelegatedDebugAdapter(items) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Debuggerman2Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_debuggerman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.list_debug_item).adapter = debugAdapter
    }

    internal fun add(item: DebuggermanItem) {
        this.items.add(item)
        debugAdapter.setItems(this.items)
    }

    internal fun add(items: Collection<DebuggermanItem>) {
        this.items.addAll(0, items)
        debugAdapter.setItems(this.items)
    }

    internal fun remove(item: DebuggermanItem) {
        this.items.remove(item)
        debugAdapter.setItems(this.items)
    }

    internal fun remove(items: Collection<DebuggermanItem>) {
        this.items.removeAll(items)
        debugAdapter.setItems(this.items)
    }

    internal fun replace(target: DebuggermanItem, item: DebuggermanItem) {
        this.items.replace(target, item)
        debugAdapter.setItems(this.items)
    }

    internal fun update(item: DebuggermanItem) {
        debugAdapter.updateItem(item)
    }
}