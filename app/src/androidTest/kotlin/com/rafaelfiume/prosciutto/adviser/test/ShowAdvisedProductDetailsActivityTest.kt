package com.rafaelfiume.prosciutto.adviser.test

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import com.rafaelfiume.prosciutto.adviser.ProductAdviserParser
import com.rafaelfiume.prosciutto.adviser.R
import com.rafaelfiume.prosciutto.adviser.ShowAdvisedProductDetailsActivity
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceForExpertResponse
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import java.util.*

@LargeTest
class ShowAdvisedProductDetailsActivityTest {

    @Rule @JvmField
    var mActivityRule: ActivityTestRule<ShowAdvisedProductDetailsActivity> = object : ActivityTestRule<ShowAdvisedProductDetailsActivity>(ShowAdvisedProductDetailsActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return ShowAdvisedProductDetailsActivity.newIntent(targetContext, mainSuggestion())
        }
    }

    @Test // TODO Check also the labels
    @Throws(InterruptedException::class)
    fun appDisplaysProductDetailsWhenReceivingThemFromAnIntent() {
        // when activity received a product with an intent (check priming above)

        // then the main suggested product is...
        onView(withId(R.id.p_detail_name)).check(matches(withText(mainSuggestion().name)))
        onView(withId(R.id.p_detail_price)).check(matches(withText(mainSuggestion().price)))
        onView(withId(R.id.p_detail_reputation)).check(matches(withText(mainSuggestion().reputation)))
        onView(withId(R.id.p_detail_fat)).check(matches(withText(mainSuggestion().fatPercentage)))

        // then show product description...
        onView(withId(R.id.description_label)).check(matches(withText(containsString("About the 'Nduja Variety:"))))
        onView(withId(R.id.p_detail_description)).check(matches(withText(containsString("La 'nduja è un salume Calabrese di consistenza morbida e dal gusto particolarmente piccante."))))
    }

    private fun mainSuggestion(): Product = allSuggestedProductsForCustomer()[0]

    private fun allSuggestedProductsForCustomer(): List<Product> = object : ArrayList<Product>() {
        init {
            add(Product("Salame Colonial", "'Nduja", "EUR 49,23", "traditional", "29,00", "http://image.url", "https://it.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles='Nduja"))
            add(Product("Salame da Fazenda", "Chorizo", "EUR 48,45", "special", "27,00", "http://image.url", ""))
            add(Product("Salame da Embolorado Não Faz Mal", "Salame Brianza D.O.P.", "EUR 33,33", "special", "27,00", "http://image.url", ""))
            addAll(ProductAdviserParser().parse(supplierAdviceForExpertResponse()))
        }
    }

}
