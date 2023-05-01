package com.tools.timezone.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.ds.PreferencesSerializer
import com.tools.timezone.repository.ds.UserPreferences
import com.tools.timezone.repository.net.ORIGIN_LIST

val Context.dataStore by dataStore("app-settings.json", PreferencesSerializer)

class MainRepository {
    private lateinit var dataStore: DataStore<UserPreferences>
    val zoneList: List<TimeZoneData> by lazy {
        initTimeZoneData()
    }

    val followedZones: LiveData<HashSet<TimeZoneData>> by lazy {
        initFollowedZones()
    }

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    private fun initTimeZoneData(): List<TimeZoneData> {
        val list = ArrayList<TimeZoneData>()
        for (i in ORIGIN_LIST.indices) {
            list.add(TimeZoneData(i, ORIGIN_LIST[i]))
        }
        return list
    }

    private fun initFollowedZones(): LiveData<HashSet<TimeZoneData>> {
        return dataStore.data.asLiveData().map { settings ->
            val set = HashSet<TimeZoneData>()
            settings.followedList.forEach { zone ->
                set.add(zone)
            }
            set
        }
    }

    fun getFollowedState(zone: TimeZoneData): Boolean {
        return followedZones.value?.contains(zone) == true
    }

    suspend fun unFollow(zone: TimeZoneData) {
        try {
            dataStore.updateData {
                val updated = ArrayList<TimeZoneData>(it.followedList)
                updated.remove(zone)
                it.copy(
                    followedList = updated
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "error when addFollow", e)
        }
    }

    suspend fun addFollow(zone: TimeZoneData) {
        try {
            dataStore.updateData {
                val updated = ArrayList<TimeZoneData>(it.followedList)
                updated.add(zone)
                it.copy(
                    followedList = updated
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "error when addFollow", e)
        }
    }

    fun getZoneById(id: Int): TimeZoneData {
        return zoneList[id]
    }

    companion object {
        private const val TAG = "MainRepository"
    }
}