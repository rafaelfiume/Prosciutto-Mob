package com.rafaelfiume.prosciutto.adviser

import com.rafaelfiume.prosciutto.adviser.ProductAdviserParserTest.ProductMatcher.Companion.isAProductNamed
import com.rafaelfiume.prosciutto.adviser.domain.Product

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Test

import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat

class ProductAdviserParserTest {

    private val subject = ProductAdviserParser()

    @Test
    fun shouldParseAdviceXmlIntoListOfSuggestedProducts() {
        // when
        val suggestedProducts = subject.parse(supplierAdviceForExpertResponse())

        // then
        assertThat(suggestedProducts, hasSize<Any>(2))

        assertThat(firstOf(suggestedProducts),
                isAProductNamed("(Traditional Less Expensive) Salume",
                        withVariety("Chorizo"),
                        costing("EUR 41,60"),
                        regardedAs("traditional"),
                        withFatPercentageOf("37,00"),
                        withImageUrl("https://upload.wikimedia.org/wikipedia/commons/3/3f/Palacioschorizo.jpg"),
                        withDescriptionUrl("https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Chorizo")))

        assertThat(secondOf(suggestedProducts),
                isAProductNamed("(Traditional More Expensive) Premium",
                        withVariety("Chorizo"),
                        costing("EUR 73,23"),
                        regardedAs("traditional"),
                        withFatPercentageOf("38,00"),
                        withImageUrl("https://upload.wikimedia.org/wikipedia/commons/3/3f/Palacioschorizo.jpg"),
                        withDescriptionUrl("https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles=Chorizo")))
    }

    private fun withVariety(s: String) = s
    private fun costing(s: String) = s
    private fun regardedAs(s: String) = s
    private fun withFatPercentageOf(s: String) = s
    private fun withImageUrl(s: String) = s
    private fun withDescriptionUrl(s: String) = s

    private fun firstOf(suggestedProducts: List<Product>): Product = suggestedProducts[0]
    private fun secondOf(suggestedProducts: List<Product>): Product = suggestedProducts[1]

    class ProductMatcher(
            private val name: String,
            private val variety: String,
            private val price: String,
            private val reputation: String,
            private val fatPercentage: String,
            private val imageUrl: String,
            private val descriptionUrl: String) : TypeSafeMatcher<Product>() {

        override fun matchesSafely(actualProduct: Product): Boolean {
            return name == actualProduct.name
                    && variety == actualProduct.variety
                    && price == actualProduct.price
                    && reputation == actualProduct.reputation
                    && fatPercentage == actualProduct.fatPercentage
                    && imageUrl == actualProduct.imageUrl
                    && descriptionUrl == actualProduct.descriptionUrl
        }

        override fun describeTo(description: Description) {
            description.appendValue(Product(name, variety, price, reputation, fatPercentage, imageUrl, descriptionUrl))
        }

        companion object {

            internal fun isAProductNamed(
                    name: String,
                    variety: String,
                    price: String,
                    reputation: String,
                    fatPercentage: String,
                    imageUrl: String,
                    descriptionUrl: String) : Matcher<in Product> {

                return ProductMatcher(name, variety, price, reputation, fatPercentage, imageUrl, descriptionUrl)
            }
        }
    }
}