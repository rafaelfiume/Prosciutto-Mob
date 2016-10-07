package com.rafaelfiume.prosciutto.adviser.integration

import com.rafaelfiume.prosciutto.adviser.ProductDescriptionParser
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription

import java.io.IOException

class ProductDescriptionQuery {

    private val parser = ProductDescriptionParser()

    @Throws(IOException::class)
    fun query(url: String): ProductDescription {
        return parser.parse(Get[url].replace("xml:space=\"preserve\"", ""))
    }

}
