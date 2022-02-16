package com.rosberry.android.debuggerman2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.entity.HeaderItem
import com.rosberry.android.debuggerman2.ui.adapter.DelegatedDebugAdapter
import com.rosberry.android.debuggerman2.ui.adapter.delegate.HeaderDelegate

open class DebugDialogFragment : BottomSheetDialogFragment() {

    private val debugAdapter: DelegatedDebugAdapter by lazy {
        DelegatedDebugAdapter(items).apply {
            delegates.add(HeaderDelegate())
        }
    }

    private val items = listOf<DebugItem>(
        HeaderItem("Test header")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.list_debug_item).run {
            adapter = debugAdapter
        }
    }
}