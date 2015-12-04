package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReturnPrimedSuccessfulResponse() throws IOException {
        whenPrimingSupplierResponseWith(supplierAdviceForExpertResponse());
        assertThat(get(supplierAdviceForExpertRequest()), containsString("<product-advisor>"));
    }

    @Test
    public void shouldThrowPrimedException() throws IOException {
        whenPrimingServerErrorWhenRequesting("/I/would/like/to/buy/bananas");

        exception.expect(RuntimeException.class);
        exception.expectMessage("Response code for url (http://localhost:8081/I/would/like/to/buy/bananas) is: 500");

        get("http://localhost:8081/I/would/like/to/buy/bananas");
    }

    private void whenPrimingSupplierResponseWith(String response) {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", response);
    }

    private void whenPrimingServerErrorWhenRequesting(String response) {
        server.primeServerErrorWhenRequesting(response);
    }

    // Duplicated :( See integration package
    private static String get(String url) throws IOException {
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) new URL(url).openConnection();

        if (http.getResponseCode() != 200) {
            // Replace by ConnectedException
            throw new RuntimeException(String.format(
                    "Response code for url (%s) is: %s", url, http.getResponseCode()));
        }

            return IOUtils.toString(http.getInputStream());
        } finally {
            http.disconnect();
        }
    }
}
