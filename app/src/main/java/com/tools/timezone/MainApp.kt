package com.tools.timezone

import android.app.Application
import com.tools.timezone.db.RoomModule

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        RoomModule.init(this)
    }
}