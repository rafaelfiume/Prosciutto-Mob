package com.rafaelfiume.prosciutto.adviser

import android.support.design.widget.Snackbar
import android.view.View

class OnFetchingProductListener (view: View, listener: View.OnClickListener): OnFetchingContentListener {

    private val requestingAdviceMessage = Snackbar.make(view, "Asking for advice...", Snackbar.LENGTH_INDEFINITE)
    private val failedMessage: Snackbar = Snackbar.make(view, "Failed", Snackbar.LENGTH_LONG).setAction("Retry", listener)

    override fun onStarted()   { requestingAdviceMessage.show()    }
    override fun onCompleted() { requestingAdviceMessage.dismiss() }
    override fun onFailure()   { failedMessage.show()              }

}