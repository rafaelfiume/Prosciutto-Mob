package com.rafaelfiume.prosciutto.adviser.test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.AdapterView
import com.rafaelfiume.prosciutto.adviser.AdviserActivity
import com.rafaelfiume.prosciutto.adviser.R
import com.rafaelfiume.prosciutto.adviser.R.id.magic_option
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class AdviserEndToEndSadPathTest {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(AdviserActivity::class.java)

    @Rule @JvmField
    var server = DependsOnServerRunningRule()

    @Before
    fun primeServerToBlowUpWhenClientRequestsExpertAdviceResponse() {
        server.primeServerErrorWhenRequesting("/salume/supplier/advise/for/Magic")
    }

    // 1. tests when server blows up

    // 2. tests connection failure (shutdown the server before the test begins)

    @Test
    fun appDisplaysSnackbarWithErrorMessageAndRetryButtonWhenUserTriesToRequestAdviceButTheServerBlowsUp() {
        // Given the user selected expert profile
        onView(withId(magic_option)).perform(click())
        onView(withId(magic_option)).check(matches(ViewMatchers.isChecked()))

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click())

        // Then...
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()))
        onView(withText("Failed")).check(matches(isDisplayed()))

        // Trying again...
        onView(withText("Retry")).perform(click())

        // Still nothing. Server is not behaving
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()))
        onView(withText("Failed")).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun appDisplaysSnackbarWithErrorMessageAndRetryButtonWhenUserTriesToRequestAdviceButTheServerIsDown() {
        // Given...
        serverIsDown()

        // ... and user selected expert profile
        onView(withId(magic_option)).perform(click())
        onView(withId(magic_option)).check(matches(ViewMatchers.isChecked()))

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click())

        // Then...
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()))
        onView(withText("Failed")).check(matches(isDisplayed()))
        onView(withText("Retry")).check(matches(isDisplayed()))
    }

    @Throws(Exception::class)
    private fun serverIsDown() {
        server.stop()
    }

    private fun anEmptyList() = object : TypeSafeMatcher<View>() {
        public override fun matchesSafely(view: View): Boolean {
            if (view !is AdapterView<*>) {
                return false
            }

            return view.adapter.count == 0
        }

        override fun describeTo(description: Description) {
            description.appendText("an empty list view")
        }

    }


}
