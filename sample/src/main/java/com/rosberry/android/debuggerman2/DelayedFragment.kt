package com.rosberry.android.debuggerman2

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class DelayedFragment : Fragment() {

    private val items = listOf(DebuggermanItem.Button("Close", "Delayed Fragment") {
        parentFragmentManager.popBackStack()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebuggermanAgent.add(items)
    }

    override fun onDestroy() {
        DebuggermanAgent.remove(items)
        super.onDestroy()
    }
}