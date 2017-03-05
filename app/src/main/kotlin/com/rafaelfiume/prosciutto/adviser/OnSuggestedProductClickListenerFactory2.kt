package com.rafaelfiume.prosciutto.adviser

import android.view.View
import com.rafaelfiume.prosciutto.adviser.domain.Product

class OnSuggestedProductClickListenerFactory2(private val mCallback: ChooseProfileAndProductFragment.OnProductSelectedListener) {

    fun newOnClickListenerFor(product: Product): View.OnClickListener {
        return View.OnClickListener { mCallback.onFragmentInteraction(product) }
    }
}