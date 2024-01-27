package com.tools.timezone.fragment.followed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.repository.MainRepository
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
internal class FollowedViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var followedViewModel: FollowedViewModel

    private val mockFollowedZones: MutableLiveData<HashSet<TimeZoneData>> = MutableLiveData(
        HashSet()
    )

    @Mock
    private lateinit var dataObserver: Observer<List<TimeZoneData>>

    @Before
    fun setUp() {
        val repository = mockk<MainRepository> {
            every { followedZones }.answers { mockFollowedZones }
        }
        followedViewModel = FollowedViewModel(repository)
        dataObserver = Mockito.mock(Observer::class.java) as Observer<List<TimeZoneData>>
    }

    @After
    fun tearDown() {
        mockFollowedZones.postValue(HashSet())
    }

    @Test
    fun getList() {
        followedViewModel.list.observeForever(dataObserver)
        val followedZones = mockFollowedZones.value ?: HashSet()
        followedZones.add(TimeZoneData(2, "Africa/Abidjan"))
        mockFollowedZones.postValue(followedZones)
        Mockito.verify(dataObserver).onChanged(followedZones.toList())
    }
}