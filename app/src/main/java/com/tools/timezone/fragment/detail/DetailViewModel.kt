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

    private val detailData: MutableLiveData<TimeZoneData> = MutableLiveData()
    val liveData: LiveData<TimeZoneData> = detailData
    private var disposable: Disposable? = null

    fun setDetailData(id: Int) {
        detailData.value = lazyRepository.get().getZoneById(id)
        Log.d(TAG, "setDetailData ${detailData.value}")
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}