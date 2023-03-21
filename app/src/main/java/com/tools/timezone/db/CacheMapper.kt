package com.tools.timezone.db

import com.tools.timezone.model.TimeZoneData

class CacheMapper : EntityMapper<TimeZoneCacheEntity, TimeZoneData> {
    override fun mapFromEntity(it: TimeZoneCacheEntity): TimeZoneData {
        return TimeZoneData(
            id = it.id,
            name = it.name,
            zone = it.zone,
            cities = it.cities,
            followed = it.followed
        )
    }

    override fun mapToEntity(it: TimeZoneData): TimeZoneCacheEntity {
        return TimeZoneCacheEntity(
            id = it.id,
            name = it.name,
            zone = it.zone,
            cities = it.cities,
            followed = it.followed
        )
    }

    fun mapFromEntityList(entities: List<TimeZoneCacheEntity>): List<TimeZoneData> {
        return entities.map { mapFromEntity(it) }
    }
}