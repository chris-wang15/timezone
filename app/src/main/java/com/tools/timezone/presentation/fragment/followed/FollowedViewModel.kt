package com.tools.timezone.presentation.fragment.followed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
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