package com.rafaelfiume.prosciutto.test;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SalumeApiContractExampleReader {

    public static String supplierAdviceForExpertRequest() {
        return readFile("com.rafaelfiume.salume.advisor.SalumeAdvisorHappyPathTest_Salume_advice_request_from_Customer_to_Supplier.txt");
    }

    public static String supplierAdviceForExpertResponse() {
        return readFile("com.rafaelfiume.salume.advisor.SalumeAdvisorHappyPathTest_Salume_advice_response_from_Supplier_to_Customer.txt");
    }

    public static String readFile(String resName) {
        try (final InputStream is = SalumeApiContractExampleReader.class.getClassLoader().getResourceAsStream(resName)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
