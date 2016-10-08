package com.rafaelfiume.prosciutto.adviser.test

import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule

import com.rafaelfiume.prosciutto.adviser.AdviserActivity
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.R
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.rafaelfiume.prosciutto.adviser.R.id.expert_option
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`

class AdviserEndToEndHappyPathTest {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(AdviserActivity::class.java)

    @Rule @JvmField
    var server = DependsOnServerRunningRule()

    @Before
    fun primeExpertAdviceResponse() {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", supplierAdviceForExpertResponse())
    }

    @Test
    fun appDisplaysSuggestedProductsWhenUserSelectsExpertProfileAndClicksOnSearchButton() {
        // Given the user selected expert profile
        onView(withId(expert_option)).perform(click())
        onView(withId(expert_option)).check(matches(ViewMatchers.isChecked()))

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click())

        //Then...
        // Check list was loaded with two suggested products
        onData(`is`(instanceOf<Any>(Product::class.java))).inAdapterView(withId(R.id.suggested_products_list)).atPosition(0).check(matches(allOf(
                hasDescendant(withText(containsString("(Traditional Less Expensive) Salume"))),
                hasDescendant(withText(containsString("EUR 41,60"))))))

        onData(`is`(instanceOf<Any>(Product::class.java))).inAdapterView(withId(R.id.suggested_products_list)).atPosition(1).check(matches(allOf(
                hasDescendant(withText(containsString("(Traditional More Expensive) Premium"))),
                hasDescendant(withText(containsString("EUR 73,23"))))))
    }

}
