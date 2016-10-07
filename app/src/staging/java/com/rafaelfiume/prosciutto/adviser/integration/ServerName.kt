package com.rafaelfiume.prosciutto.adviser.integration

object ServerName {

    fun name(): String {
        return System.getenv("SUPPLIER_STAGING_URL")
    }

}
