package com.rosberry.android.debuggerman2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebugItem
import com.rosberry.android.debuggerman2.ui.adapter.DelegatedDebugAdapter
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ButtonDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.HeaderDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.InputDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.SelectorDelegate
import com.rosberry.android.debuggerman2.ui.adapter.delegate.ToggleDelegate

open class DebugDialogFragment : BottomSheetDialogFragment() {

    private enum class SelectorValue {
        VALUE_1, VALUE_2, VALUE_3
    }

    private val items = listOf<DebugItem>(
        DebugItem.Header("Test header"),
        DebugItem.Toggle("Test toggle") {},
        DebugItem.Button("Test button") {},
        DebugItem.Input("Test input", null, EditorInfo.IME_ACTION_SEARCH) {},
        DebugItem.Input("Test input") {},
        DebugItem.Selector("Test selector", SelectorValue.values().toList()) {}
    )

    private val debugAdapter: DelegatedDebugAdapter by lazy {
        DelegatedDebugAdapter(items).apply {
            delegates.add(
                HeaderDelegate(),
                ToggleDelegate(),
                ButtonDelegate(),
                InputDelegate(),
                SelectorDelegate()
            )
        }
    }

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

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}