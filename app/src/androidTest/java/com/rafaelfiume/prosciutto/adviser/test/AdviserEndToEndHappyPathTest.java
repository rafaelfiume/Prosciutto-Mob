package com.rafaelfiume.prosciutto.adviser.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.AdviserActivity;
import com.rafaelfiume.prosciutto.adviser.domain.Product;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.adviser.R.id.expert_option;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class AdviserEndToEndHappyPathTest {

    @Rule
    public ActivityTestRule<AdviserActivity> mActivityRule = new ActivityTestRule<>(AdviserActivity.class);

    @Rule
    public DependsOnServerRunningRule server = new DependsOnServerRunningRule();

    @Before
    public void primeExpertAdviceResponse() {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", supplierAdviceForExpertResponse());
    }

    @Test
    public void appDisplaysSuggestedProductsWhenUserSelectsExpertProfileAndClicksOnSearchButton() {
        // Given the user selected expert profile
        onView(withId(expert_option)).perform(click());
        onView(withId(expert_option)).check(matches(ViewMatchers.isChecked()));

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click());

        //Then...
        // Check list was loaded with two suggested products
        onData(is(instanceOf(Product.class)))
                .inAdapterView(withId(R.id.suggested_products_list))
                .atPosition(0)
                .check(matches(allOf(
                        hasDescendant(withText(containsString("(Traditional Less Expensive) Salume"))),
                        hasDescendant(withText(containsString("EUR 41,60"))))));

        onData(is(instanceOf(Product.class)))
                .inAdapterView(withId(R.id.suggested_products_list))
                .atPosition(1)
                .check(matches(allOf(
                        hasDescendant(withText(containsString("(Traditional More Expensive) Premium"))),
                        hasDescendant(withText(containsString("EUR 73,23"))))));
    }

}
