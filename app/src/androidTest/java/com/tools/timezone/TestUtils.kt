package com.tools.timezone

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun<T> LiveData<T>.getOrWaitValue(): T {
    var data : T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            this@getOrWaitValue.removeObserver(this)
            latch.countDown()
        }
    }

    this.observeForever(observer)

    try {
        if (!latch.await(4, TimeUnit.SECONDS)) {
            throw TimeoutException("LiveData can not get data")
        }
    } finally {
        removeObserver(observer)
    }

    return data as T
}