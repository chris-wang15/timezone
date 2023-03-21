package com.tools.timezone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TimeZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cacheEntity: TimeZoneCacheEntity): Long

    @Query("SELECT * FROM time_zone_table")
    fun get(): Single<List<TimeZoneCacheEntity>>

    @Query("SELECT * FROM time_zone_table where followed = 1")
    fun getFollowed(): Single<List<TimeZoneCacheEntity>>

    // state = 1 true; 0 false
    @Query("UPDATE time_zone_table set followed = :state where id = :id")
    fun changeFollowedState(id: Int, state: Int): Single<Int>

    @Query("SELECT * FROM time_zone_table where id = :id")
    fun getZoneById(id: Int): Single<TimeZoneCacheEntity>
}