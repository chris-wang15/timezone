package com.tools.timezone.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimeZoneData(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("followed")
    var followed: Boolean = false
) : Serializable {
    companion object {
        private const  val serialVersionUID = -83L
    }
}
