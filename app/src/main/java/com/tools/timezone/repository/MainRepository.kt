package com.tools.timezone.repository

import android.util.Log
import com.tools.timezone.db.CacheMapper
import com.tools.timezone.db.RoomModule
import com.tools.timezone.db.TimeZoneDao
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.net.RetrofitBuilder
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object MainRepository {
    private const val TAG = "MainRepository"

    private val dao: TimeZoneDao = RoomModule.dao
    private val cacheMapper: CacheMapper = CacheMapper()

    fun resetTimeZoneData(): Observable<List<TimeZoneData>> {
        return RetrofitBuilder.getFakeDataList().map {
            val netData = it.list
            for (data in netData) {
                dao.insert(cacheMapper.mapToEntity(data))
            }
        }
            .flatMap {
                dao.get().toObservable().map {
                    val list: MutableList<TimeZoneData> = ArrayList()
                    for (data in it) {
                        list.add(cacheMapper.mapFromEntity(data))
                    }
                    return@map list
                }
            }
    }

    fun getCachedTimeZoneData(): Single<List<TimeZoneData>> {
        return dao.get().map {
            val list: MutableList<TimeZoneData> = ArrayList()
            for (data in it) {
                list.add(cacheMapper.mapFromEntity(data))
            }
            return@map list
        }
    }

    fun getFollowedZones(): Single<List<TimeZoneData>> {
        return dao.getFollowed().map {
            val list: MutableList<TimeZoneData> = ArrayList()
            for (data in it) {
                list.add(cacheMapper.mapFromEntity(data))
            }
            return@map list
        }
    }


    fun changeFollowedState(id: Int, followed: Boolean): Disposable {
        val state = if(followed) 1 else 0
        return dao.changeFollowedState(id, state).subscribeOn(Schedulers.io()).subscribe(
            { updateResult -> Log.i(TAG, "changeFollowedState success $updateResult") },
            { e -> Log.e(TAG, "changeFollowedState error: ", e) }
        )
    }
}