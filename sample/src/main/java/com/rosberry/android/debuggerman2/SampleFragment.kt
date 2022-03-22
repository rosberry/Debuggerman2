package com.rosberry.android.debuggerman2

import androidx.fragment.app.Fragment

open class SampleFragment(layoutId: Int = 0) : Fragment(layoutId) {

    protected val activity: SampleActivity get() = requireActivity() as SampleActivity
}