package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.adviser.Product;
import com.rafaelfiume.prosciutto.test.StubbedServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductAdviserQueryTest {

    private StubbedServer server = new StubbedServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void shouldReturnListOfSuggestedProductsWhenQueryingAdviserWebService() throws Exception {
        givenASuccessfulAdviceResponseForExpertsIs(supplierAdviceForExpertResponse());

        //when
        final List<Product> suggestedProducts = EXPERT.suggestedProducts();

        // then
        assertThat(suggestedProducts, hasSize(2));
    }

    private void givenASuccessfulAdviceResponseForExpertsIs(String response) {
        server.primeSuccesfulResponse("/salume/supplier/advise/for/Expert", response);
    }
}
