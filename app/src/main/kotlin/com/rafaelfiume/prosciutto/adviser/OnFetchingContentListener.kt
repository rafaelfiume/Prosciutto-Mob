package com.rafaelfiume.prosciutto.adviser

import android.support.design.widget.Snackbar.LENGTH_INDEFINITE
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.design.widget.Snackbar.make
import android.view.View

class OnFetchingContentListener (view: View?, listener: View.OnClickListener){

    private val requestingAdviceMessage = make(view!!, "Asking for advice...", LENGTH_INDEFINITE)
    private val failedMessage           = make(view!!, "Failed", LENGTH_LONG).setAction("Retry", listener)

    fun onStarted()   { requestingAdviceMessage.show()    }
    fun onCompleted() { requestingAdviceMessage.dismiss() }
    fun onFailure()   { failedMessage.show()              }

}