package com.tools.timezone.fragment.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val detailData: MutableLiveData<TimeZoneData> = MutableLiveData()
    val liveData: LiveData<TimeZoneData> = detailData
    private var disposable: Disposable? = null

    fun setDetailData(id: Int) {
        detailData.value = MainRepository.getZoneById(id)
        Log.d(TAG, "setDetailData ${detailData.value}")
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}