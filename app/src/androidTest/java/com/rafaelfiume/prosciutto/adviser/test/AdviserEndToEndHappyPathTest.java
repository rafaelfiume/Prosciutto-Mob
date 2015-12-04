package com.rafaelfiume.prosciutto.adviser.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.AdviserActivity;
import com.rafaelfiume.prosciutto.adviser.Product;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.test.StubbedServer;

import org.junit.After;
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

    private StubbedServer server = new StubbedServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @Before
    public void primeSuggestProductsForExpertResponse() {
        server.primeSuccesfulResponse("/salume/supplier/advise/for/Expert", supplierAdviceForExpertResponse());
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void appDisplaySuggestedProductsWhenClientSelectsExpertProfileAndClicksOnSearchButton() {
        // Given the user selected expert profile
        onView(withId(expert_option)).perform(click());
        onView(withId(expert_option)).check(matches(ViewMatchers.isChecked()));

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click());

        // Check list was loaded with two advices
        onData(is(instanceOf(Product.class)))
                .inAdapterView(withId(R.id.products_list))
                .atPosition(0)
                .check(matches(hasDescendant(allOf(
                        withId(R.id.product_name_text),
                        withText(containsString("Traditional Salume"))))));

        onData(is(instanceOf(Product.class)))
                .inAdapterView(withId(R.id.products_list))
                .atPosition(1)
                .check(matches(hasDescendant(allOf(
                        withId(R.id.product_name_text),
                        withText(containsString("Premium Salume"))))));

        // The following works as well
//            onView(withId(R.id.products_list))
//                    .check(matches(withAdaptedData(withItemContent("Traditional Salume"))));


    }

}
