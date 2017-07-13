package com.rafaelfiume.prosciutto.adviser


interface OnFetchingContentListener {

    fun onStarted()
    fun onCompleted()
    fun onFailure()

}