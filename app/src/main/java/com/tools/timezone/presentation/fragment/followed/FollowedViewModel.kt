package com.tools.timezone.presentation.fragment.followed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import com.tools.timezone.domain.usecase.FollowStateCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowedViewModel
@Inject constructor(followStateCase: FollowStateCase) : ViewModel() {

    val list: LiveData<List<TimeZoneData>> = followStateCase.followedZone.map { set ->
        set.toList().sortedWith(compareBy { it.id })
    }
}