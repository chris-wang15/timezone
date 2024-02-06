package com.tools.timezone.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.domain.repository.MainRepository
import com.tools.timezone.domain.usecase.ZoneCase
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ZoneCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockZoneList: List<TimeZoneData> = listOf(
        TimeZoneData(0, "Africa/Abidjan"),
        TimeZoneData(1, "America/New_York")
    )

    private lateinit var zoneCase: ZoneCase

    @Before
    fun setUp() {
        val repository = mockk<MainRepository> {
            every { loadTimeZoneData() }.answers { mockZoneList }
        }
        zoneCase = ZoneCase { repository }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getZoneById() {
        val illegalId = -1
        assert(zoneCase.getZoneById(illegalId) == null)
        val id = 0
        assert(zoneCase.getZoneById(id) == mockZoneList[0])
    }
}