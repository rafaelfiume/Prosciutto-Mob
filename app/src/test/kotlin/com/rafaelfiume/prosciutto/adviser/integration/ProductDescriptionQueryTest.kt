package com.rafaelfiume.prosciutto.adviser.integration

import org.junit.Test

import java.io.IOException

import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertThat

class ProductDescriptionQueryTest {

    private val url = "https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Bastardei"

    private val expectedDescription = "I bastardei sono un prodotto agroalimentare tradizionale della Lombardia, consistente in salamini in budello naturale di carne mista bovina e suina."

    private val underTest = ProductDescriptionQuery()

    @Test
    @Throws(IOException::class)
    fun shouldRetrieveAndParseProductDescription() {
        assertThat(underTest.query(url).value(), containsString(expectedDescription))
    }

}
