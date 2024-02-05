package com.tools.timezone.domain.repository

import androidx.lifecycle.LiveData
import com.tools.timezone.domain.model.TimeZoneData

interface MainRepository {

    val zoneList: List<TimeZoneData>

    val followedZones: LiveData<HashSet<TimeZoneData>>

    fun getFollowedState(zone: TimeZoneData): Boolean

    suspend fun unFollow(zone: TimeZoneData)

    suspend fun addFollow(zone: TimeZoneData)

    fun getZoneById(id: Int): TimeZoneData

}