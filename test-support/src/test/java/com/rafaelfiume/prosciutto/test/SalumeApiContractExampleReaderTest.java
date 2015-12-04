package com.rafaelfiume.prosciutto.test;

import org.junit.Test;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertRequest;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class SalumeApiContractExampleReaderTest {

    //
    // Checking if request/response test setup is all right
    //

    @Test
    public void chekingRequestExampleIsNotNull() {
        assertThat(supplierAdviceForExpertRequest(), containsString("/salume/supplier/advise/for/"));
    }

    @Test
    public void chekingResponseExampleIsNotNull() {
        assertThat(supplierAdviceForExpertResponse(), containsString("<product-advisor>"));
    }

}
