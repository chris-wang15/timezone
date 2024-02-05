package com.tools.timezone.presentation.fragment.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import javax.inject.Inject

class DetailViewModel
@Inject
constructor(private val lazyRepository: dagger.Lazy<MainRepository>) : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _detailData: MutableLiveData<TimeZoneData> = MutableLiveData()
    val detailData: LiveData<TimeZoneData> = _detailData

    fun setDetailData(id: Int) {
        _detailData.value = lazyRepository.get().getZoneById(id)
        Log.d(TAG, "setDetailData ${_detailData.value}")
    }
}