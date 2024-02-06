package com.tools.timezone.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import com.tools.timezone.domain.usecase.FollowStateCase
import com.tools.timezone.getOrWaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FollowStateCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockZoneList: List<TimeZoneData> = listOf(
        TimeZoneData(0, "Africa/Abidjan"),
        TimeZoneData(1, "America/New_York")
    )

    private val mockFollowedZones: MutableLiveData<HashSet<TimeZoneData>> = MutableLiveData(
        hashSetOf()
    )

    private lateinit var followStateCase: FollowStateCase

    @Before
    fun setUp() {
        val repository = mockk<MainRepository> {
            every { loadTimeZoneData() }.answers { mockZoneList }
            every { loadFollowedZones() }.answers { mockFollowedZones }
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
        }
        followStateCase = FollowStateCase { repository }
    }

    @After
    fun tearDown() {
        mockFollowedZones.postValue(hashSetOf())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateFollowState() = runBlocking {
        val zone = mockZoneList[0]
        val testDispatcher = UnconfinedTestDispatcher()
        var updateJob = launch(testDispatcher) {
            followStateCase.addFollow(zone)
            delay(1)
            // Simulate duplicate follow
            followStateCase.addFollow(zone)
        }
        followStateCase.followedZone.getOrWaitValue()
        assert(followStateCase.isFollowed(zone))
        assert(followStateCase.followedZone.value?.size == 1)
        updateJob.cancel()
        updateJob= launch(testDispatcher) {
            followStateCase.unFollow(zone)
        }
        assert(!followStateCase.isFollowed(zone))
        updateJob.cancel()
    }
}