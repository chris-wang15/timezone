package com.tools.timezone.repository.ds

import com.google.gson.annotations.SerializedName
import com.tools.timezone.model.TimeZoneData
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerializedName("followedList")
    val followedList: List<TimeZoneData> = listOf()
)