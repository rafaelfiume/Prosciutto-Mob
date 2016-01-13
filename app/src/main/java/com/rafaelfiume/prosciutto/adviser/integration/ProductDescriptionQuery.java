package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.adviser.ProductDescriptionParser;
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription;

import java.io.IOException;

import static com.rafaelfiume.prosciutto.adviser.integration.Get.get;

public class ProductDescriptionQuery {

    private final ProductDescriptionParser parser = new ProductDescriptionParser();

    public ProductDescription query(String url) throws IOException {
        return parser.parse(get(url).replace("xml:space=\"preserve\"", ""));
    }

}
