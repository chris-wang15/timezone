package com.tools.timezone.fragment.followed

import android.util.Log
import androidx.lifecycle.*
import com.tools.timezone.MainApp
import com.tools.timezone.dataStore
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
import com.tools.timezone.repository.ds.UserPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FollowedViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowedViewModel"
    }

    // viewModel should not have any lifecycle
    val myList: LiveData<UserPreferences> = MainApp.App.dataStore.data.asLiveData()

    private val followedData: MutableLiveData<List<TimeZoneData>> = MutableLiveData()
    val list: LiveData<List<TimeZoneData>> = followedData
    private var disposable: Disposable? = null

    fun getFollowedLists() {
//        viewModelScope.launch {
//            MainApp.App.dataStore.data
//                .map {
//                    Log.e("FollowedViewModel", "show map: ${it.shownList} " +
//                            "map on ${Thread.currentThread()}")
//                    it.shownList.size
//                }
//                .flowOn(Dispatchers.IO)
//                .collect {
//                    Log.e("FollowedViewModel", "collect on ${Thread.currentThread()}")
//                    testValue = it
//                }
//        }

        if (disposable != null) {
            Log.e(TAG, "last disposable not finished")
            disposable?.dispose()
        }
        disposable = MainRepository.getFollowedZones()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    followedData.value = it
                    disposable = null
                },
                { e ->
                    Log.e(TAG, "getLists error", e)
                    disposable = null
                }
            )
    }

    fun setValue() {
        viewModelScope.launch(Dispatchers.IO) {
            MainApp.App.dataStore.updateData {
                Log.e("FollowedViewModel", "setValue on ${Thread.currentThread()}")
                val newList = ArrayList<String>(it.shownList)
                newList.add("${myList.value?.shownList?.size ?: -1}")
                it.copy(
                    shownList = newList
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}