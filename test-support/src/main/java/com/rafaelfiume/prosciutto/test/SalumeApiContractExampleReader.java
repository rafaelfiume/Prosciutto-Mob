package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SalumeApiContractExampleReader {

    public static String supplierAdviceForExpertRequest() {
        return readFile("AdviseProductBasedOnCustomerProfileEndToEndTest/request.onlySuggestTraditionalProductsWithCheapestOnesFirstToExperts.txt");
    }

    public static String supplierAdviceForExpertResponse() {
        return readFile("AdviseProductBasedOnCustomerProfileEndToEndTest/response.onlySuggestTraditionalProductsWithCheapestOnesFirstToExperts.txt");
    }

    public static String readFile(String resName) {
        try (final InputStream is = SalumeApiContractExampleReader.class.getClassLoader().getResourceAsStream(resName)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
