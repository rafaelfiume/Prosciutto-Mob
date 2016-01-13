package com.rafaelfiume.prosciutto.adviser.integration;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ProductDescriptionQueryTest {

    private final String url =
            "https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Bastardei";

    private final String expectedDescription = "I bastardei sono un prodotto agroalimentare tradizionale della Lombardia, consistente in salamini in budello naturale di carne mista bovina e suina.";

    private final ProductDescriptionQuery underTest = new ProductDescriptionQuery();

    @Test
    public void shouldRetrieveAndParseProductDescription() throws IOException {
        assertThat(underTest.query(url).value(), containsString(expectedDescription));
    }

}
