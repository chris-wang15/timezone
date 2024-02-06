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

    override fun loadTimeZoneData(): List<TimeZoneData> {
        val list = ArrayList<TimeZoneData>()
        for (i in ORIGIN_LIST.indices) {
            list.add(TimeZoneData(i, ORIGIN_LIST[i]))
        }
        return list
    }

    override fun loadFollowedZones(): LiveData<HashSet<TimeZoneData>> {
        return dataStore.data.asLiveData().map { settings ->
            val set = HashSet<TimeZoneData>()
            settings.followedList.forEach { zone ->
                set.add(zone)
            }
            set
        }
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

    companion object {
        private const val TAG = "MainRepository"
    }
}