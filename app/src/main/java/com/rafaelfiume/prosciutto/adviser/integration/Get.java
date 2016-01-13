package com.rafaelfiume.prosciutto.adviser.integration;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Get {

    private Get() {
        // not instantiable
    }

    public static String get(String url) throws IOException {
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
