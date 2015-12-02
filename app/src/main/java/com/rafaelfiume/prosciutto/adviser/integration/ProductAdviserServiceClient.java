package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.adviser.Product;

import java.util.List;

public interface ProductAdviserServiceClient {

    List<Product> suggestedProducts() throws Exception;
}
