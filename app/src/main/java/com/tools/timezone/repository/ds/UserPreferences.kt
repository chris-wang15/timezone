package com.tools.timezone.repository.ds

import com.google.gson.annotations.SerializedName
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    @SerializedName("shownList")
    val shownList: List<String> = listOf()
)