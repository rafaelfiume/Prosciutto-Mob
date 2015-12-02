package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.test.StubbedServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductAdviserQueryTest {

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
    public void shouldReturnListOfSuggestedProductsWhenQueringAdiviserWebService() throws Exception {
        assertThat(EXPERT.suggestedProducts(), hasSize(2));
    }
}
