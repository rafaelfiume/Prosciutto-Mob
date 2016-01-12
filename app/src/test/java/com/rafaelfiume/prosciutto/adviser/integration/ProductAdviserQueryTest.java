package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.adviser.domain.Product;
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductAdviserQueryTest {

    @Rule
    public DependsOnServerRunningRule server = new DependsOnServerRunningRule();

    @Test
    public void shouldReturnListOfSuggestedProductsWhenQueryingAdviserWebService() throws Exception {
        givenASuccessfulAdviceResponseForExpertsIs(supplierAdviceForExpertResponse());

        //when
        final List<Product> suggestedProducts = EXPERT.suggestedProducts();

        // then
        assertThat(suggestedProducts, hasSize(2));
    }

    private void givenASuccessfulAdviceResponseForExpertsIs(String response) {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", response);
    }
}
