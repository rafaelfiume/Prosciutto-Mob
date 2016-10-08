package com.rafaelfiume.prosciutto.adviser.domain

class ProductDescription(private val value: String) {

    fun value(): String {
        return value
    }

    companion object {

        fun empty(): ProductDescription {
            return ProductDescription("")
        }
    }
}
