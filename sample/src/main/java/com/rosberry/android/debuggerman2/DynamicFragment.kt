package com.rosberry.android.debuggerman2

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class DynamicFragment : Fragment(R.layout.fragment_dynamic) {

    private val activity: SampleActivity get() = requireActivity() as SampleActivity

    private val items: List<DebuggermanItem> = listOf(
        DebuggermanItem.Toggle("Dynamic toggle", "Dynamic controls") {},
        DebuggermanItem.Button("Dynamic button", "Dynamic controls") {},
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.addDebugItems(items)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.removeDebugItems(items)
    }
}