package com.tools.timezone

import android.app.Application
import com.tools.timezone.repository.MainRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        App = this
        MainRepository.init(this)
    }

    companion object {
        lateinit var App: Application
    }
}