package com.rafaelfiume.prosciutto.adviser

import android.support.design.widget.Snackbar
import android.view.View

class OnFetchingContentListener (view: View, listener: View.OnClickListener){

    private val requestingAdviceMessage = Snackbar.make(view, "Asking for advice...", Snackbar.LENGTH_INDEFINITE)
    private val failedMessage: Snackbar = Snackbar.make(view, "Failed", Snackbar.LENGTH_LONG).setAction("Retry", listener)

    fun onStarted()   { requestingAdviceMessage.show()    }
    fun onCompleted() { requestingAdviceMessage.dismiss() }
    fun onFailure()   { failedMessage.show()              }

}