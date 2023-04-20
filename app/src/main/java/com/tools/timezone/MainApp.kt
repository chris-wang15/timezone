package com.tools.timezone

import android.app.Application
import com.tools.timezone.repository.db.RoomModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        RoomModule.init(this)
    }
}