package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertRequest;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class StubbedServerTest {

    @Rule
    public DependsOnServerRunningRule server = new DependsOnServerRunningRule();

    @Test
    public void checkFakeServerWorks() throws IOException {
        whenPrimingSupplierResponseWith(supplierAdviceForExpertResponse());
        assertThat(get(supplierAdviceForExpertRequest()), containsString("<product-advisor>"));
    }

    private void whenPrimingSupplierResponseWith(String response) {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", response);
    }

    private static String get(String url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();

        if (http.getResponseCode() != 200) {
            // Replace by ConnectedException
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
