package com.tools.timezone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        App = this
    }

    companion object {
        lateinit var App: Application
    }
}