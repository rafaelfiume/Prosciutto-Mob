package com.rafaelfiume.prosciutto.adviser.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.rafaelfiume.prosciutto.adviser.AdviserActivity;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
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

    //
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
