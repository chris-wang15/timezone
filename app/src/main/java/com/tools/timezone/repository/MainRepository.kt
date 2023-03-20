package com.tools.timezone.repository

import android.util.Log
import com.tools.timezone.db.CacheMapper
import com.tools.timezone.db.RoomModule
import com.tools.timezone.db.TimeZoneCacheEntity
import com.tools.timezone.db.TimeZoneDao
import com.tools.timezone.net.RetrofitBuilder
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRepository
constructor(

    private val cacheMapper: CacheMapper
) {
    companion object {
        private const val TAG = "MainRepository"
    }

    private val dao: TimeZoneDao = RoomModule.dao

    fun resetTimeZoneData(): Single<List<TimeZoneCacheEntity>> {
        return RetrofitBuilder.getFakeDataList().map {
            val netData = it.list
            for (data in netData) {
                dao.insert(cacheMapper.mapToEntity(data))
            }
        }
            .singleOrError()
            .flatMap {
                return@flatMap dao.get()
            }
    }

    fun getCachedTimeZoneData(): Single<List<TimeZoneCacheEntity>> {
        return dao.get()
    }

    fun getFollowedCars(): Single<List<TimeZoneCacheEntity>> {
        return dao.getFollowed()
    }


    fun changeFollowedState(id: Int, followed: Boolean): Disposable {
        val state = if(followed) 1 else 0
        return dao.changeFollowedState(id, state).subscribeOn(Schedulers.io()).subscribe(
            { updateResult -> Log.i(TAG, "changeFollowedState success $updateResult") },
            { e -> Log.e(TAG, "changeFollowedState error: ", e) }
        )
    }
}