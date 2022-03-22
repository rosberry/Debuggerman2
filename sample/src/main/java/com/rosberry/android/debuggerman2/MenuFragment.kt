package com.rosberry.android.debuggerman2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuFragment : SampleFragment(R.layout.fragment_menu) {

    private var delayedJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.run {
            findViewById<View>(R.id.btn_crash_1).setOnClickListener { throw Exception("Crash button 1 Pressed") }
            findViewById<View>(R.id.btn_crash_2).setOnClickListener { throw Exception("Crash button 2 Pressed") }
            findViewById<View>(R.id.btn_dynamic).setOnClickListener { openDynamicFragment() }
            findViewById<View>(R.id.btn_delayed).setOnClickListener { openDelayedFragment() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        delayedJob?.cancel()
    }

    private fun openDynamicFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_app, DynamicFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openDelayedFragment() {
        if (delayedJob?.isActive != true) delayedJob = lifecycleScope.launch {
            delay(2000)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_app, DelayedFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}