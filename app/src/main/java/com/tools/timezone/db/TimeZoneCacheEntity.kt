package com.tools.timezone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "time_zone_table")
@TypeConverters(ItemConverter::class)
data class TimeZoneCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo("name")
    val name: String = "",
    @ColumnInfo("zone")
    val zone: Int = 0,
    @ColumnInfo("cities")
    val cities: List<String> = ArrayList(),
    @ColumnInfo("followed")
    var followed: Boolean = false
) : Serializable
