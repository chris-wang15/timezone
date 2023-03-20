package com.tools.timezone.db

import android.app.Application
import androidx.room.Room

object RoomModule {

    lateinit var dao: TimeZoneDao

    val mapper: CacheMapper = CacheMapper()

    fun init(context: Application) {
        dao = Room.databaseBuilder(
            context,
            TimeZoneDataBase::class.java,
            TimeZoneDataBase.DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
            .timeZoneDao()
    }
}