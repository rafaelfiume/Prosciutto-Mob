package com.rafaelfiume.prosciutto.adviser

import android.view.View
import com.rafaelfiume.prosciutto.adviser.domain.Product

class OnSuggestedProductClickListenerFactory(private val mCallback: ChooseProfileAndProductFragment.OnProductSelectedListener) {

    fun newOnClickListenerFor(product: Product): View.OnClickListener {
        return View.OnClickListener { mCallback.onSelected(product) }
    }
}