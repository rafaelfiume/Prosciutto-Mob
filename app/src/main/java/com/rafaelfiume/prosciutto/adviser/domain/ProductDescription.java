package com.rafaelfiume.prosciutto.adviser.domain;

public class ProductDescription {

    private final String value;

    public static final ProductDescription empty() {
        return new ProductDescription("");
    }

    public ProductDescription(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
