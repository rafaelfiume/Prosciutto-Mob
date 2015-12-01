package com.rafaelfiume.prosciutto.adviser.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.rafaelfiume.prosciutto.adviser.AdviserActivity;
import com.rafaelfiume.prosciutto.adviser.Product;
import com.rafaelfiume.prosciutto.adviser.R;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rafaelfiume.prosciutto.adviser.R.id.expert_option;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AdviserEndToEndTest {

    @Rule
    public ActivityTestRule<AdviserActivity> mActivityRule = new ActivityTestRule<>(AdviserActivity.class);

    private StubbedServer server = new StubbedServer();

    @Before
    public void primeSupplierResponse() throws Exception {
        server.start();
    }

    @After
    public void stopStubbedServer() throws Exception {
        server.stop();
    }

    @Test
    public void checkAppReceivesAdvise() {
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

    //
    // TODO RF 31/10/15 Move these tests to another place
    // Checking if request/response test setup is all right
    //

    @Test
    public void chekingRequestExampleIsNotNull() {
        assertThat(supplierAdviceRequest(), containsString("/salume/supplier/advise/for/"));
    }

    @Test
    public void chekingResponseExampleIsNotNull() {
        assertThat(supplierAdviceResponse(), containsString("<product-advisor>"));
    }

    @Test
    public void checkFakeServerWorks() throws IOException {
        assertThat(get(supplierAdviceRequest()), containsString("<product-advisor>"));
    }

    private static String get(String url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();

        if (http.getResponseCode() != 200) {
            throw new AssertionError(String.format(
                    "Response code for url (%s) is: %s", url, http.getResponseCode()));
        }

        try {
            return IOUtils.toString(http.getInputStream());
        } finally {
            http.disconnect();
        }
    }

    private static String supplierAdviceRequest() {
        return readFile("com.rafaelfiume.salume.advisor.SalumeAdvisorHappyPathTest_Salume_advice_request_from_Customer_to_Supplier.txt");
    }

    // TODO RF 31/10/2015 Duplicated
    private static String supplierAdviceResponse() {
        return readFile("com.rafaelfiume.salume.advisor.SalumeAdvisorHappyPathTest_Salume_advice_response_from_Supplier_to_Customer.txt");
    }

    private static String readFile(String resName) {
        try (final InputStream is = AdviserEndToEndTest.class.getClassLoader().getResourceAsStream(resName)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
