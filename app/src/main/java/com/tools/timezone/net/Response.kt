package com.tools.timezone.net

import com.google.gson.annotations.SerializedName
import com.tools.timezone.model.TimeZoneData
import java.io.Serializable

class Response : Serializable {
    @SerializedName("list")
    val list: List<TimeZoneData> = ArrayList()

    @SerializedName("code")
    val code: String = ""

    companion object {
        private const  val serialVersionUID = -584818873L
    }
}