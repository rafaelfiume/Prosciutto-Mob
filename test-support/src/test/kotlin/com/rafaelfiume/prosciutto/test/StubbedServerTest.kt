package com.rafaelfiume.prosciutto.test

import org.apache.commons.io.IOUtils
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertRequest
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertThat

class StubbedServerTest {

    @Rule @JvmField
    var server = DependsOnServerRunningRule()

    @Rule @JvmField
    var exception = ExpectedException.none()

    @Test
    @Throws(IOException::class)
    fun shouldReturnPrimedSuccessfulResponse() {
        whenPrimingSupplierResponseWith(supplierAdviceForExpertResponse())
        assertThat(get(supplierAdviceForExpertRequest()), containsString("<product-advisor>"))
    }

    @Test
    @Throws(IOException::class)
    fun shouldThrowPrimedException() {
        whenPrimingServerErrorWhenRequesting("/I/would/like/to/buy/bananas")

        exception.expect(RuntimeException::class.java)
        exception.expectMessage("Response code for url <<http://localhost:8081/I/would/like/to/buy/bananas>> is: 500")

        get("http://localhost:8081/I/would/like/to/buy/bananas")
    }

    private fun whenPrimingSupplierResponseWith(response: String) {
        server.primeSuccessfulResponse("/salume/supplier/advise/for/Expert", response)
    }

    private fun whenPrimingServerErrorWhenRequesting(response: String) {
        server.primeServerErrorWhenRequesting(response)
    }

    // TODO Duplicated :( See integration package
    @Throws(IOException::class)
    private operator fun get(url: String): String {
        var http: HttpURLConnection? = null
        try {
            http = URL(url).openConnection() as HttpURLConnection

            if (http.responseCode != 200) {
                throw RuntimeException("Response code for url <<$url>> is: ${http.responseCode}")
            }

            return IOUtils.toString(http.inputStream)
            
        } finally {
            http!!.disconnect()
        }
    }
}
