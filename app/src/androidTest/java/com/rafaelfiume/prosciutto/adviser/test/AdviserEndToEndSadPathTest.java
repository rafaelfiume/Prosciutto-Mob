package com.rafaelfiume.prosciutto.adviser.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.rafaelfiume.prosciutto.adviser.AdviserActivity;
import com.rafaelfiume.prosciutto.adviser.R;
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.adviser.R.id.magic_option;
import static java.lang.String.format;

public class AdviserEndToEndSadPathTest {

    @Rule
    public ActivityTestRule<AdviserActivity> mActivityRule = new ActivityTestRule<>(AdviserActivity.class);

    @Rule
    public DependsOnServerRunningRule server = new DependsOnServerRunningRule();

    @Before
    public void primeServerToBlowUpWhenClientRequestsExpertAdviceResponse() {
        server.primeServerErrorWhenRequesting("/salume/supplier/advise/for/Magic");
    }

    // 1. tests when server blows up

    // 2. tests connection failure (shutdown the server before the test begins)

    @Test
    public void appDisplaysSnackbarWithErrorMessageAndRetryButtonWhenUserTriesToRequestAdviceButTheServerBlowsUp() {
        // Given the user selected expert profile
        onView(withId(magic_option)).perform(click());
        onView(withId(magic_option)).check(matches(ViewMatchers.isChecked()));

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click());

        // Then...
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()));
        onView(withText("Failed")).check(matches(isDisplayed()));

        // Trying again...
        onView(withText("Retry")).perform(click());

        // Still nothing. Server is not behaving
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()));
        onView(withText("Failed")).check(matches(isDisplayed()));
    }

    @Test
    public void appDisplaysSnackbarWithErrorMessageAndRetryButtonWhenUserTriesToRequestAdviceButTheServerIsDown() throws Exception {
        // Given...
        serverIsDown();

        // ... and user selected expert profile
        onView(withId(magic_option)).perform(click());
        onView(withId(magic_option)).check(matches(ViewMatchers.isChecked()));

        // When user clicks on search button
        onView(withId(R.id.fab)).perform(click());

        // Then...
        onView(withId(R.id.suggested_products_list)).check(matches(anEmptyList()));
        onView(withText("Failed")).check(matches(isDisplayed()));
        onView(withText("Retry")).check(matches(isDisplayed()));
    }

    private void serverIsDown() throws Exception {
        server.stop();
    }

    private static Matcher<View> anEmptyList() {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }

                @SuppressWarnings("rawtypes")
                final Adapter adapter = ((AdapterView) view).getAdapter();
                return adapter.getCount() == 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("an empty list view");
            }

            @Override
            protected void describeMismatchSafely(View actual, Description mismatchDescription) {
                mismatchDescription.appendText(format("found a view \"%s\"", actual));
            }
        };
    }

}
