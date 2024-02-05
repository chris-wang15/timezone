package com.tools.timezone.data.ds

import com.google.gson.annotations.SerializedName
import com.tools.timezone.domain.model.TimeZoneData
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerializedName("followedList")
    val followedList: List<TimeZoneData> = listOf()
)