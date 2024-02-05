package com.tools.timezone

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.tools.timezone.presentation.activity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class MainActivityTest {

    @Before
    fun setUp() {
    }

    @Test
    fun testMainActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.fab))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.item_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.item_search))
            .perform(ViewActions.typeText("Chongqing"))
        Espresso.onView(withId(R.id.search_button))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.item_list))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickChildViewWithId(R.id.follow_switch)
                )
            )
        Espresso.pressBack() // exit type board
        Espresso.pressBack() // exit cur fragment
        // Thread.sleep(100) // no need
        Espresso.onView(withId(R.id.followed_item_list))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(1)))
    }
}