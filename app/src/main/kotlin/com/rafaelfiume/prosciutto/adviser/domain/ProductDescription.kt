package com.rafaelfiume.prosciutto.adviser.domain

class ProductDescription(val value: String) {

    companion object {

        fun empty(): ProductDescription {
            return ProductDescription("")
        }
    }
}
