package com.rosberry.android.debuggerman2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.adapter.DelegatedDebugAdapter

open class DebugDialogFragment : BottomSheetDialogFragment() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    private val addButton = DebugItem.Button("Add dynamic controls", 0, ::addItem)
    private val dynamicButton = DebugItem.Button("Remove dynamic input", 0, ::removeItem)
    private val dynamicInput = DebugItem.Input("Dynamic input", null, EditorInfo.IME_ACTION_SEARCH) {}
    private val items = listOf(
        DebugItem.Header("Example static header"),
        DebugItem.Toggle("Example static toggle") {},
        DebugItem.Button("Example static button") {},
        DebugItem.Input("Example static input") {},
        DebugItem.Selector("Example static selector", SelectorValue.values().toList()) {},
        addButton
    )

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
        view.findViewById<RecyclerView>(R.id.list_debug_item).run {
            adapter = debugAdapter
        }
    }

    private fun addItem() {
        debugAdapter.setItems(items - addButton + dynamicInput + dynamicButton)
    }

    private fun removeItem() {
        debugAdapter.setItems(items)
    }
}