package com.tools.timezone.fragment.followed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowedViewModel
@Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
//    companion object {
//        private const val TAG = "FollowedViewModel"
//    }

    val list: LiveData<List<TimeZoneData>> = mainRepository.followedZones.map { set ->
        set.toList().sortedWith(compareBy { it.id })
    }
}