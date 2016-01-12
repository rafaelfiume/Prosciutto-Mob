package com.rafaelfiume.prosciutto.adviser.integration;

import com.rafaelfiume.prosciutto.adviser.domain.Product;
import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public enum ProductAdviserQuery implements ProductAdviserServiceClient {

    MAGIC {
        @Override
        public List<Product> suggestedProducts() throws Exception {
            return retrieveProductsSuggestionsFor("Magic");
        }
    },

    HEALTHY {
        @Override
        public List<Product> suggestedProducts() throws Exception {
            return retrieveProductsSuggestionsFor("Healthy");
        }
    },

    GOURMET {
        @Override
        public List<Product> suggestedProducts() throws Exception {
            return retrieveProductsSuggestionsFor("Gourmet");
        }
    },

    // Missing integration tests for the other three methods above
    EXPERT {
        @Override
        public List<Product> suggestedProducts() throws Exception {
            return this.retrieveProductsSuggestionsFor("Expert");
        }
    };

    protected List<Product> retrieveProductsSuggestionsFor(String profile) throws Exception {
        return parser.parse(get(ADVISER_WEB_SERVICE + profile));
    }

    private static final String ADVISER_WEB_SERVICE = ServerName.name() + "/advise/for/";

    private final ProductAdviserParser parser = new ProductAdviserParser();

    private static String get(String url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();

        if (http.getResponseCode() != 200) {
            throw new ResourceNotFoundException(String.format(
                    "Response code for url (%s) is: %s", url, http.getResponseCode()));
        }

        try {
            return IOUtils.toString(http.getInputStream());
        } finally {
            http.disconnect();
        }
    }
}
