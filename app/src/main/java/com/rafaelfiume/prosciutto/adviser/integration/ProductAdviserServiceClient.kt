package com.rafaelfiume.prosciutto.adviser.integration

import com.rafaelfiume.prosciutto.adviser.domain.Product

interface ProductAdviserServiceClient {

    @Throws(Exception::class)
    fun suggestedProducts(): List<Product>
}
