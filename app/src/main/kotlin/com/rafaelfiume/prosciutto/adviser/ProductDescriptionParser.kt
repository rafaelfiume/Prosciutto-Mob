package com.rafaelfiume.prosciutto.adviser

import android.util.Log

import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription

import com.rafaelfiume.prosciutto.adviser.util.Xml.getValueFrom
import com.rafaelfiume.prosciutto.adviser.util.Xml.xmlFrom

class ProductDescriptionParser {

    fun parse(xml: String): ProductDescription {
        try {
            return ProductDescription(getValueFrom(xmlFrom(xml), "//extract"))
        } catch (e: Exception) {
            Log.e(ProductDescriptionParser::class.java.name, "error when parsing xml $xml", e)
            throw RuntimeException("could not parse advice xml: $xml", e)
        }

    }

}
