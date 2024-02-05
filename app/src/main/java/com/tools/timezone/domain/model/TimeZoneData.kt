package com.tools.timezone.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TimeZoneData(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
) {
    override fun toString(): String {
        return "id $id, name $name"
    }
}
