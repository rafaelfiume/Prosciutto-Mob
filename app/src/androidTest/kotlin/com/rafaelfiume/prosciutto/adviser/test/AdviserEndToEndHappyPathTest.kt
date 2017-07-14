package com.rafaelfiume.prosciutto.adviser.test

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule

import com.rafaelfiume.prosciutto.adviser.AdviserActivity
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.R
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import com.rafaelfiume.prosciutto.adviser.R.id.expert_option
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import android.view.WindowManager

@LargeTest
class AdviserEndToEndHappyPathTest {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(AdviserActivity::class.java)

    @Rule @JvmField
    var server = DependsOnServerRunningRule()

    @Before
    fun unlockScreen() {
        val activity = mActivityRule.activity
        val wakeUpDevice = Runnable {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
    }

    @Before
    fun primeExpertAdviceResponse() {
        // TODO 29/06/2017 : Issue #9 : Invoke this priming in the given section of the tests and make it return a Product for a better readability
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", supplierAdviceForExpertResponse())
    }

    @Test
    fun appShowsProductDetails_WhenUserSelectsOneOfTheListedProducts() {
        // Given the user selected a product
        onView(withId(expert_option)).perform(click())
        onView(withId(expert_option)).check(matches(ViewMatchers.isChecked()))
        onView(withId(R.id.fab)).perform(click())

        // When the user selects the second listed product
        onData(`is`(instanceOf<Any>(Product::class.java))).inAdapterView(withId(R.id.productsList)).atPosition(1).check(matches(allOf(
                hasDescendant(withText(containsString("(Traditional More Expensive) Premium"))),
                hasDescendant(withText(containsString("EUR 73,23")))))).perform(click())

        // Then the suggested product is...
        onView(withId(R.id.productName)).check(matches(withText("(Traditional More Expensive) Premium")))
        onView(withId(R.id.productPrice)).check(matches(withText("EUR 73,23")))
        onView(withId(R.id.productReputation)).check(matches(withText("traditional")))
        onView(withId(R.id.productFat)).check(matches(withText("38,00")))
        onView(withId(R.id.aboutLabel)).check(matches(withText(containsString("About the Chorizo Variety:"))))
        // TODO 29/06/2017 : Issue #9 : This is brittle since it depends on real content from production
        onView(withId(R.id.productDescription)).check(matches(withText(containsString("Chorizo (chouriço in portoghese, chorizu in asturiano, chourizo in galiziano, xoriço in catalano)"))))
    }

    @Test
    fun appShowsPreviousListOfProducts_WhenBackFromDetails() {
        // Given the user selected a product
        onView(withId(expert_option)).perform(click())
        onView(withId(expert_option)).check(matches(ViewMatchers.isChecked()))
        onView(withId(R.id.fab)).perform(click())
        onData(`is`(instanceOf<Any>(Product::class.java))).inAdapterView(withId(R.id.productsList)).atPosition(1).check(matches(allOf(
                hasDescendant(withText(containsString("(Traditional More Expensive) Premium"))),
                hasDescendant(withText(containsString("EUR 73,23")))))).perform(click())

        // When...
        pressBack()

        // Then
        onData(`is`(instanceOf<Any>(Product::class.java))).inAdapterView(withId(R.id.productsList)).atPosition(1).check(matches(allOf(
                hasDescendant(withText(containsString("(Traditional More Expensive) Premium"))),
                hasDescendant(withText(containsString("EUR 73,23"))))))
    }

}
