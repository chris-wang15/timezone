package com.tools.timezone.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimeZoneCacheEntity::class], version = 1)
abstract class TimeZoneDataBase: RoomDatabase() {

    abstract fun timeZoneDao(): TimeZoneDao

    companion object {
        const val DATA_BASE_NAME = "time_zone_db"
    }
}