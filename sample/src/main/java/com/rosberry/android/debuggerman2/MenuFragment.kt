package com.rosberry.android.debuggerman2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(R.layout.fragment_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.run {
            findViewById<View>(R.id.btn_crash).setOnClickListener { throw Exception() }
            findViewById<View>(R.id.btn_dynamic).setOnClickListener { openDynamicFragment() }
        }
    }

    private fun openDynamicFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_app, DynamicFragment())
            .addToBackStack(null)
            .commit()
    }
}