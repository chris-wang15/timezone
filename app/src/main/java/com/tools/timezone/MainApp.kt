package com.tools.timezone

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import com.tools.timezone.repository.db.RoomModule
import com.tools.timezone.repository.ds.PreferencesSerializer
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore by dataStore("app-settings.json", PreferencesSerializer)

@HiltAndroidApp
class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        RoomModule.init(this)
        App = this
    }

    companion object {
        lateinit var App: Application
    }
}