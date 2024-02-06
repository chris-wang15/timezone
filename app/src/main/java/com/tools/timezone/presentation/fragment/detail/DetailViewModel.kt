package com.tools.timezone.presentation.fragment.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import com.tools.timezone.domain.usecase.ZoneCase
import javax.inject.Inject

class DetailViewModel
@Inject
constructor(private val zoneCase: ZoneCase) : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _detailData: MutableLiveData<TimeZoneData> = MutableLiveData()
    val detailData: LiveData<TimeZoneData> = _detailData

    fun setDetailData(id: Int) {
        val zone = zoneCase.getZoneById(id) ?: return
        _detailData.postValue(zone)
        Log.d(TAG, "setDetailData $zone")
    }
}