package com.rafaelfiume.prosciutto.test

import org.junit.Test

import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertRequest
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertThat

class SalumeApiContractExampleReaderTest {

    //
    // Checking if request/response test setup is all right
    //

    @Test
    fun checkingRequestExampleIsNotNull() {
        assertThat(supplierAdviceForExpertRequest(), containsString("/salume/supplier/advise/for/"))
    }

    @Test
    fun checkingResponseExampleIsNotNull() {
        assertThat(supplierAdviceForExpertResponse(), containsString("<product-advisor>"))
    }

}
