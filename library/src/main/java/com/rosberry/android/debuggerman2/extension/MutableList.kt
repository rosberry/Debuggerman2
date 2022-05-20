package com.rosberry.android.debuggerman2.extension

internal fun <T : Any> MutableList<T>.replace(target: T, item: T) {
    val index = indexOf(target)
    if (index >= 0) {
        remove(target)
        add(index, item)
    }
}