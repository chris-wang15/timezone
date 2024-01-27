package com.tools.timezone.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tools.timezone.repository.MainRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class CachedViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var cachedViewModel: CachedViewModel

    private val mockZoneList: List<TimeZoneData> = listOf(
        TimeZoneData(0, "Africa/Abidjan"),
        TimeZoneData(1, "America/New_York")
    )

    private val mockFollowedZones: MutableLiveData<HashSet<TimeZoneData>> = MutableLiveData(
        HashSet()
    )

    @Mock
    private lateinit var dataObserver: Observer<List<TimeZoneData>>

    @Mock
    private lateinit var followedObserver: Observer<HashSet<TimeZoneData>>

    @Before
    fun setUp() {
        val repository = mockk<MainRepository> {
            every { zoneList }.answers { mockZoneList }
            every { followedZones }.answers { mockFollowedZones }
            every { getZoneById(any()) }.answers {
                val id = firstArg<Int>()
                zoneList[id]
            }
            coEvery { addFollow(any()) }.coAnswers {
                val followed = mockFollowedZones.value ?: HashSet()
                followed.add(firstArg())
                mockFollowedZones.postValue(followed)
            }
            coEvery { unFollow(any()) }.coAnswers {
                val followed = mockFollowedZones.value ?: HashSet()
                followed.remove(firstArg())
                mockFollowedZones.postValue(followed)
            }
            every { getFollowedState(any()) }.answers {
                mockFollowedZones.value?.contains(firstArg()) == true
            }
        }
        cachedViewModel = CachedViewModel(repository)
        dataObserver = Mockito.mock(Observer::class.java) as Observer<List<TimeZoneData>>
        followedObserver = Mockito.mock(Observer::class.java) as Observer<HashSet<TimeZoneData>>
    }

    @After
    fun tearDown() {
        mockFollowedZones.postValue(HashSet())
    }

    @Test
    fun resetZoneList() {
        cachedViewModel.list.observeForever(dataObserver)
        cachedViewModel.resetZoneList()
        Mockito.verify(dataObserver, Mockito.atLeastOnce()).onChanged(mockZoneList)
    }

    @Test
    fun searchTimeZone() {
        cachedViewModel.list.observeForever(dataObserver)
        cachedViewModel.resetZoneList()
        cachedViewModel.searchTimeZone("Africa")
        Mockito.verify(dataObserver).onChanged(
            listOf(TimeZoneData(0, "Africa/Abidjan"))
        )
    }

    @Test
    fun updateFollowState() {
        cachedViewModel.followed.observeForever(followedObserver)
        cachedViewModel.updateFollowState(0, true)
        Mockito.verify(followedObserver, Mockito.atLeastOnce()).onChanged(
            hashSetOf(TimeZoneData(0, "Africa/Abidjan"))
        )
        cachedViewModel.updateFollowState(0, false)
        Mockito.verify(followedObserver, Mockito.atLeastOnce()).onChanged(
            hashSetOf()
        )
    }
}