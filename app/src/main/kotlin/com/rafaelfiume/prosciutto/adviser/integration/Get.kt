package com.rafaelfiume.prosciutto.adviser.integration

import org.apache.commons.io.IOUtils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object Get {

    @Throws(IOException::class)
    operator fun get(url: String): String {
        val http = URL(url).openConnection() as HttpURLConnection

        if (http.responseCode != 200) {
            throw ResourceNotFoundException("Response code for url $url is: ${http.responseCode}")
        }

        try {
            return IOUtils.toString(http.inputStream)
        } finally {
            http.disconnect()
        }
    }
}
