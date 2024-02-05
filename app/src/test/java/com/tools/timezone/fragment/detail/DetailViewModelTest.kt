package com.tools.timezone.fragment.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tools.timezone.domain.model.TimeZoneData
import com.tools.timezone.presentation.fragment.detail.DetailViewModel
import com.tools.timezone.domain.repository.MainRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class DetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var detailViewModel: DetailViewModel

    private val mockTimeZoneData = TimeZoneData(2, "Africa/Abidjan")

    @Mock
    private lateinit var dataObserver: Observer<TimeZoneData>

    @Before
    fun setUp() {
        val repository = mockk<MainRepository> {
            every { getZoneById(any()) }.answers {
                println("getZoneById called")
                mockTimeZoneData
            }
        }
        detailViewModel = DetailViewModel { repository }
//         dataObserver = mockk()
        dataObserver = mock(Observer::class.java) as Observer<TimeZoneData>
    }

    @After
    fun tearDown() {
    }

    @Test
    fun setDetailData() {
        detailViewModel.detailData.observeForever(dataObserver)
        detailViewModel.setDetailData(2)
        verify(dataObserver).onChanged(mockTimeZoneData)
    }
}