package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceRequest;
import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceResponse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.containsString;

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
