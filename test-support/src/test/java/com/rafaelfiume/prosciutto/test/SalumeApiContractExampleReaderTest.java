package com.rafaelfiume.prosciutto.test;

import org.junit.Test;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceRequest;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceResponse;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class SalumeApiContractExampleReaderTest {

    //
    // Checking if request/response test setup is all right
    //

    @Test
    public void chekingRequestExampleIsNotNull() {
        assertThat(supplierAdviceRequest(), containsString("/salume/supplier/advise/for/"));
    }

    @Test
    public void chekingResponseExampleIsNotNull() {
        assertThat(supplierAdviceResponse(), containsString("<product-advisor>"));
    }

}
