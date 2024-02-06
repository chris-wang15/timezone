package com.tools.timezone.domain.usecase

import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZoneCase @Inject constructor(private val lazyRepository: dagger.Lazy<MainRepository>) {
    val allZoneData : List<TimeZoneData> by lazy {
        lazyRepository.get().loadTimeZoneData()
    }

    fun getZoneById(id: Int): TimeZoneData? {
        if (id >= 0 && id < allZoneData.size) {
            return allZoneData[id]
        }
        return null
    }
}