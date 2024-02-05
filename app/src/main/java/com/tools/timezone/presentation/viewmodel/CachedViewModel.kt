package com.tools.timezone.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CachedViewModel
@Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    companion object {
        private const val TAG = "CachedViewModel"
    }

    private val innerList: MutableLiveData<List<TimeZoneData>> = MutableLiveData(
        mainRepository.zoneList
    )
    val list: LiveData<List<TimeZoneData>> = innerList
    val followed: LiveData<HashSet<TimeZoneData>> = mainRepository.followedZones

    fun resetZoneList() {
        innerList.value = mainRepository.zoneList
    }

    // todo combine list and typed text
    fun searchTimeZone(name: String?) {
        if (name.isNullOrEmpty()) {
            innerList.value = mainRepository.zoneList
            return
        }
        val updated = mainRepository.zoneList.filter {
            it.name.contains(name)
        }
        innerList.value = updated
    }

    fun updateFollowState(id: Int, follow: Boolean) {
        val zone = mainRepository.getZoneById(id)
        val contains = followed.value!!.contains(zone)
        if (follow && !contains) {
            viewModelScope.launch {
                mainRepository.addFollow(zone)
            }
        } else if (!follow && contains) {
            viewModelScope.launch {
                mainRepository.unFollow(zone)
            }
        } else {
            Log.e(TAG, "follow state error: $zone $follow")
        }
    }

    fun getFollowedState(zone: TimeZoneData): Boolean {
        return mainRepository.getFollowedState(zone)
    }

    override fun onCleared() {
        super.onCleared()
        innerList.value = mainRepository.zoneList
    }
}