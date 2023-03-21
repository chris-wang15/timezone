package com.tools.timezone.repository.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemConverter {
    @TypeConverter
    fun stringToObject(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<String>): String {
        return Gson().toJson(list)
    }
}