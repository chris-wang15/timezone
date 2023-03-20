package com.tools.timezone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimeZoneData(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("zone")
    val zone: Int = 0,
    @SerializedName("cities")
    val cities: List<String> = ArrayList(),
    @SerializedName("followed")
    var followed: Boolean = false
) : Serializable {
    companion object {
        private const  val serialVersionUID = -83L
    }
}
