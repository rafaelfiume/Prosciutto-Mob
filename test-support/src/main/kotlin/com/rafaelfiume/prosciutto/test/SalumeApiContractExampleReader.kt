package com.rafaelfiume.prosciutto.test

import org.apache.commons.io.IOUtils
import java.io.IOException

object SalumeApiContractExampleReader {

    fun supplierAdviceForExpertRequest(): String = readFile("AdviseProductBasedOnCustomerProfileEndToEndTest/request.onlySuggestTraditionalProductsWithCheapestOnesFirstToExperts.txt")

    fun supplierAdviceForExpertResponse(): String = readFile("AdviseProductBasedOnCustomerProfileEndToEndTest/response.onlySuggestTraditionalProductsWithCheapestOnesFirstToExperts.txt")

    fun readFile(resName: String): String {
        try {
            SalumeApiContractExampleReader::class.java.classLoader
                    .getResourceAsStream(resName).use { `is` -> return IOUtils.toString(`is`) }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}
