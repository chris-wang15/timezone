package com.tools.timezone.repository.ds

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerializedName("showCompleted")
    val showCompleted: Boolean
)