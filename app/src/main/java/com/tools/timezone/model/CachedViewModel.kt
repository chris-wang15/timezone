package com.tools.timezone.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CachedViewModel: ViewModel() {

    companion object {
        private const val TAG = "CachedViewModel"
    }

    private val allZones: MutableLiveData<List<TimeZoneData>> = MutableLiveData()
    val list: LiveData<List<TimeZoneData>> = allZones
    private var disposable: Disposable? = null

    fun getCachedLists() {
        disposable?.dispose()
        disposable = MainRepository.getCachedTimeZoneData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { allZones.value = it},
                {e -> Log.e(TAG, "getLists error", e)}
            )
    }

    fun resetCache() {
        disposable?.dispose()
        disposable = MainRepository.resetTimeZoneData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { allZones.value = it},
                {e -> Log.e(TAG, "getLists error", e)}
            )
    }

    fun updateFollowState(id: Int, follow: Boolean) {
        MainRepository.changeFollowedState(id, follow)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}