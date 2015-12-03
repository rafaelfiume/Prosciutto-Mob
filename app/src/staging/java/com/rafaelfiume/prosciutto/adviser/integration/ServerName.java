package com.rafaelfiume.prosciutto.adviser.integration;

public class ServerName {

    public static String name() {
        return System.getenv("SUPPLIER_STAGING_URL");
    }

}
