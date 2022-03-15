package com.rosberry.android.debuggerman2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.adapter.DelegatedDebugAdapter

open class DebuggermanDialog : BottomSheetDialogFragment() {

    private val items: MutableList<DebugItem> = mutableListOf()

    private val debugAdapter: DelegatedDebugAdapter by lazy { DelegatedDebugAdapter(items) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Debuggerman2Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        view.findViewById<RecyclerView>(R.id.list_debug_item).run {
            adapter = debugAdapter
        }
    }

    protected open fun setup() {}

    protected fun add(vararg items: DebugItem) {
        this.items.addAll(items)
        debugAdapter.setItems(this.items)
    }

    protected fun remove(vararg items: DebugItem) {
        this.items.removeAll(items)
        debugAdapter.setItems(this.items)
    }
}