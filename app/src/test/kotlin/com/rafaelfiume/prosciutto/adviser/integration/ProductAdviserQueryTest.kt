package com.rafaelfiume.prosciutto.adviser.integration

import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT
import com.rafaelfiume.prosciutto.test.DependsOnServerRunningRule
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class ProductAdviserQueryTest {

    @Rule @JvmField
    var server = DependsOnServerRunningRule()

    @Test
    @Throws(Exception::class)
    fun shouldReturnListOfSuggestedProductsWhenQueryingAdviserWebService() {
        givenASuccessfulAdviceResponseForExpertsIs(supplierAdviceForExpertResponse())

        val suggestedProducts = EXPERT.suggestedProducts()

        assertThat(suggestedProducts, hasSize<Any>(2))
    }

    private fun givenASuccessfulAdviceResponseForExpertsIs(response: String) {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", response)
    }
}
