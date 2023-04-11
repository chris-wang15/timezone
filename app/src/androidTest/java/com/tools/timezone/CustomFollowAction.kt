package com.tools.timezone

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher


fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "clickChildViewWithId $id"
        }

        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun perform(uiController: UiController?, view: View?) {
            view?.findViewById<View>(id)?.performClick()
        }
    }
}