package com.tools.timezone.fragment.followed

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tools.timezone.getOrWaitValue
import com.tools.timezone.repository.db.TimeZoneDataBase
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FollowedViewModelTest {

    private lateinit var viewModel: FollowedViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before // before every test
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, TimeZoneDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        viewModel = FollowedViewModel()
    }

    @Test
    fun testFollowFunction() {
        viewModel.getFollowedLists()

        val result = viewModel.list.getOrWaitValue().find {
            it.followed == true
        }

        println("result: $result")
        assert(result != null)
    }

}