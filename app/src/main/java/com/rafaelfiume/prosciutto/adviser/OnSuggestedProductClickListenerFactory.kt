package com.rafaelfiume.prosciutto.adviser

import android.app.Activity
import android.view.View

import com.rafaelfiume.prosciutto.adviser.domain.Product

// Use an interface when having more factories like this one?
class OnSuggestedProductClickListenerFactory(private val callingActivity: Activity) {

    fun newOnClickListenerFor(product: Product): View.OnClickListener {
        return View.OnClickListener { ShowAdvisedProductDetailsActivity.navigate(callingActivity, product) }
    }
}
