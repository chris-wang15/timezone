package com.tools.timezone.domain.usecase

import androidx.lifecycle.LiveData
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowStateCase @Inject constructor(private val lazyRepository: dagger.Lazy<MainRepository>) {
    val followedZone : LiveData<HashSet<TimeZoneData>> by lazy {
        lazyRepository.get().loadFollowedZones()
    }

    fun isFollowed(zone: TimeZoneData): Boolean {
        return followedZone.value?.contains(zone) == true
    }

    suspend fun addFollow(zone: TimeZoneData) {
        if (!isFollowed(zone)) {
            lazyRepository.get().addFollow(zone)
        }
    }

    suspend fun unFollow(zone: TimeZoneData) {
        if (isFollowed(zone)) {
            lazyRepository.get().unFollow(zone)
        }
    }
}