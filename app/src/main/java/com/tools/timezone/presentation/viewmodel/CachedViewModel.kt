package com.tools.timezone.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.usecase.FollowStateCase
import com.tools.timezone.domain.usecase.ZoneCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CachedViewModel
@Inject constructor(
    private val zoneCase: ZoneCase,
    private val followStateCase: FollowStateCase,
) : ViewModel() {

    companion object {
        private const val TAG = "CachedViewModel"
    }

    private val innerList: MutableLiveData<List<TimeZoneData>> = MutableLiveData(
        zoneCase.allZoneData
    )
    val list: LiveData<List<TimeZoneData>> = innerList
    val followed: LiveData<HashSet<TimeZoneData>> = followStateCase.followedZone

    fun resetZoneList() {
        innerList.value = zoneCase.allZoneData
    }

    fun searchTimeZone(name: String?) {
        if (name.isNullOrEmpty()) {
            innerList.value = zoneCase.allZoneData
            return
        }
        val updated = zoneCase.allZoneData.filter {
            it.name.contains(name)
        }
        innerList.value = updated
    }

    fun updateFollowState(id: Int, follow: Boolean) {
        val zone = zoneCase.getZoneById(id)
        if (zone == null) {
            Log.e(TAG, "Can not find zone with id $id")
            return
        }
        viewModelScope.launch {
            if (follow) {
                followStateCase.addFollow(zone)
            } else {
                followStateCase.unFollow(zone)
            }
        }
    }

    fun isFollowed(zone: TimeZoneData): Boolean {
        return followStateCase.isFollowed(zone)
    }

    override fun onCleared() {
        super.onCleared()
        innerList.value = zoneCase.allZoneData
    }
}