package com.tools.timezone.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tools.timezone.repository.MainRepository
import kotlinx.coroutines.launch

class CachedViewModel : ViewModel() {

    companion object {
        private const val TAG = "CachedViewModel"
    }

    private val innerList: MutableLiveData<List<TimeZoneData>> = MutableLiveData(
        MainRepository.zoneList
    )
    val list: LiveData<List<TimeZoneData>> = innerList
    val followed: LiveData<HashSet<TimeZoneData>> = MainRepository.followedZones

    // todo combine list and typed text
    fun searchTimeZone(name: String?) {
        if (name.isNullOrEmpty()) {
            innerList.value = MainRepository.zoneList
            return
        }
        val updated = innerList.value?.filter {
            it.name.contains(name)
        } ?: emptyList()
        innerList.value = updated
    }

    fun updateFollowState(id: Int, follow: Boolean) {
        val zone = MainRepository.getZoneById(id)
        val contains = followed.value!!.contains(zone)
        if (follow && !contains) {
            viewModelScope.launch {
                MainRepository.addFollow(zone)
            }
        } else if (!follow && contains) {
            viewModelScope.launch {
                MainRepository.unFollow(zone)
            }
        } else {
            Log.e(TAG, "follow state error: $zone $follow")
        }
    }

    fun getFollowedState(zone: TimeZoneData): Boolean {
        return MainRepository.getFollowedState(zone)
    }

    override fun onCleared() {
        super.onCleared()
        innerList.value = MainRepository.zoneList
    }
}