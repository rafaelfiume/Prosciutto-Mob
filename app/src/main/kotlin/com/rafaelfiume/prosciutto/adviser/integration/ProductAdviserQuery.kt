package com.rafaelfiume.prosciutto.adviser.integration

import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.integration.Get.get

enum class ProductAdviserQuery : ProductAdviserServiceClient {

    MAGIC {
        @Throws(Exception::class)
        override fun suggestedProducts(): List<Product> {
            return retrieveProductsSuggestionsFor("Magic")
        }
    },

    HEALTHY {
        @Throws(Exception::class)
        override fun suggestedProducts(): List<Product> {
            return retrieveProductsSuggestionsFor("Healthy")
        }
    },

    GOURMET {
        @Throws(Exception::class)
        override fun suggestedProducts(): List<Product> {
            return retrieveProductsSuggestionsFor("Gourmet")
        }
    },

    // Missing integration tests for the other three methods above
    EXPERT {
        @Throws(Exception::class)
        override fun suggestedProducts(): List<Product> {
            return this.retrieveProductsSuggestionsFor("Expert")
        }
    };

    @Throws(Exception::class)
    protected fun retrieveProductsSuggestionsFor(profile: String): List<Product> {
        return parser.parse(get(ADVISER_WEB_SERVICE + profile))
    }

    private val parser = ProductAdviserParser()

    companion object {

        private val ADVISER_WEB_SERVICE = ServerName.name() + "/advise/for/"
    }

}
