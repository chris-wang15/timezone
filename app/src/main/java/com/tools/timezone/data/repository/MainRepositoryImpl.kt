package com.tools.timezone.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.tools.timezone.data.ds.PreferencesSerializer
import com.tools.timezone.data.ds.UserPreferences
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import com.tools.timezone.domain.repository.net.ORIGIN_LIST

private val Context.dataStore by dataStore("app-settings.json", PreferencesSerializer)

class MainRepositoryImpl(context: Context) : MainRepository {
    private val dataStore: DataStore<UserPreferences> = context.dataStore

    override val zoneList: List<TimeZoneData> by lazy {
        initTimeZoneData()
    }

    override val followedZones: LiveData<HashSet<TimeZoneData>> by lazy {
        initFollowedZones()
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

    override fun getFollowedState(zone: TimeZoneData): Boolean {
        return followedZones.value?.contains(zone) == true
    }

    override suspend fun unFollow(zone: TimeZoneData) {
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

    override suspend fun addFollow(zone: TimeZoneData) {
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

    override fun getZoneById(id: Int): TimeZoneData {
        return zoneList[id]
    }

    companion object {
        private const val TAG = "MainRepository"
    }
}