package com.tools.timezone.fragment.followed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository

class FollowedViewModel : ViewModel() {
//    companion object {
//        private const val TAG = "FollowedViewModel"
//    }

    val list: LiveData<List<TimeZoneData>> = MainRepository.followedZones.map { set ->
        set.toList().sortedWith(compareBy { it.id })
    }
}