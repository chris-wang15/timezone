package com.tools.timezone.fragment.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
import io.reactivex.disposables.Disposable
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