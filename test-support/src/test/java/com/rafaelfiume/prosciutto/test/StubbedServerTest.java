package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceRequest;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class StubbedServerTest {

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
}
