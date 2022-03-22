package com.rosberry.android.debuggerman2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class MenuFragment : SampleFragment(R.layout.fragment_menu) {

    private var delayedJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.run {
            findViewById<View>(R.id.btn_crash).setOnClickListener { throw Exception() }
            findViewById<View>(R.id.btn_dynamic).setOnClickListener { openDynamicFragment() }
            findViewById<View>(R.id.btn_delayed).setOnClickListener { openDelayedFragment() }
        }
    }

    private fun openDynamicFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_app, DynamicFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openDelayedFragment() {
        if (delayedJob?.isActive != true) delayedJob = lifecycleScope.launchWhenStarted {
            delay(2000)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_app, DelayedFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}