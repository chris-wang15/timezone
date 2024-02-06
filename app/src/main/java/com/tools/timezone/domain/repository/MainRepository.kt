package com.tools.timezone.domain.repository

import androidx.lifecycle.LiveData
import com.tools.timezone.domain.model.TimeZoneData

interface MainRepository {

    fun loadTimeZoneData(): List<TimeZoneData>

    fun loadFollowedZones(): LiveData<HashSet<TimeZoneData>>

    suspend fun unFollow(zone: TimeZoneData)

    suspend fun addFollow(zone: TimeZoneData)
}