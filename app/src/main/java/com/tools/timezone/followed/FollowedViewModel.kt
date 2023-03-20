package com.tools.timezone.followed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FollowedViewModel: ViewModel() {

    companion object {
        private const val TAG = "FollowedViewModel"
    }

    private val followedData: MutableLiveData<List<TimeZoneData>> = MutableLiveData()
    val list: LiveData<List<TimeZoneData>> = followedData
    private var disposable: Disposable? = null

    fun getFollowedLists() {
        if (disposable != null) {
            Log.e(TAG, "last disposable not finished")
            disposable?.dispose()
        }
        disposable = MainRepository.getFollowedZones().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { followedData.value = it},
                {e -> Log.e(TAG, "getLists error", e)}
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}