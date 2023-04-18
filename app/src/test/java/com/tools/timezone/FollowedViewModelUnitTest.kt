package com.tools.timezone

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tools.timezone.fragment.followed.FollowedViewModel
import com.tools.timezone.repository.db.TimeZoneDataBase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FollowedViewModelUnitTest {
    // https://www.youtube.com/watch?v=Lh2avATK-xU&t=452s&ab_channel=CheezyCode
//    private val testDispatcher = StandardTestDispacher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewModel: FollowedViewModel

    @Before // before every test
    fun setUp() {
        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testFollowUnit() {
        viewModel.getFollowedLists()

        val result = viewModel.list.getOrWaitValue().find {
            it.followed == true
        }

        println("result: $result")
        assert(result != null)
    }

}