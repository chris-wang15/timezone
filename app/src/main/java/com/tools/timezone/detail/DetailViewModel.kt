package com.tools.timezone.detail

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

    fun getDetailData(id: Int) {
        if (disposable != null) {
            Log.e(TAG, "last detail disposable not finished")
            disposable?.dispose()
        }
        disposable = MainRepository.getZoneById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    detailData.value = it
                    disposable = null
                },
                { e ->
                    Log.e(TAG, "get data error", e)
                    disposable = null
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}