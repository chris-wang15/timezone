package com.tools.timezone.repository

import android.util.Log
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.db.CacheMapper
import com.tools.timezone.repository.db.RoomModule
import com.tools.timezone.repository.db.TimeZoneCacheEntity
import com.tools.timezone.repository.db.TimeZoneDao
import com.tools.timezone.repository.net.ORIGIN_LIST
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object MainRepository {
    private const val TAG = "MainRepository"

    private val dao: TimeZoneDao = RoomModule.dao
    private val cacheMapper: CacheMapper = CacheMapper()

    fun resetTimeZoneData(): Observable<List<TimeZoneData>> {
        return Observable.create {
            it.onNext(ORIGIN_LIST)
        }.map {
            for (i in it.indices) {
                dao.insert(TimeZoneCacheEntity(
                    i,
                    it[i],
                    false
                ))
            }
        }
            .flatMap {
                dao.get().toObservable().map {
                    cacheMapper.mapFromEntityList(it)
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

    fun searchCachedTimeZoneData(zoneName: String): Single<List<TimeZoneData>> {
        return dao.searchName(zoneName).map {
            cacheMapper.mapFromEntityList(it)
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

    fun getZoneById(id: Int): Single<TimeZoneData> {
        return dao.getZoneById(id).map {
            cacheMapper.mapFromEntity(it)
        }
    }
}