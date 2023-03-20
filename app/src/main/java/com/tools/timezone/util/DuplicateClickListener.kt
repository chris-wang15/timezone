package com.tools.timezone.util

import android.view.View

abstract class DuplicateClickListener(private val intervalMs: Long) : View.OnClickListener {
    private var lastClickTime: Long = -1L

    constructor() : this(1000)

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime == -1L || currentTime - lastClickTime > intervalMs) {
            onCLick(v)
            lastClickTime = currentTime
        } else {
            onFastClick()
        }
    }

    abstract fun onCLick(v: View?)

    open fun onFastClick() {}
}