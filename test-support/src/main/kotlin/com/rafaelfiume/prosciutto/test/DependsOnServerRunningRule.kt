package com.rafaelfiume.prosciutto.test

import org.junit.rules.ExternalResource

class DependsOnServerRunningRule : ExternalResource() {

    private val server = StubbedServer()

    fun primeSuccessfulResponse(contextPath: String, response: String) {
        server.primeSuccessfulResponse(contextPath, response)
    }

    fun primeServerErrorWhenRequesting(contextPath: String) {
        server.primeServerErrorWhenRequesting(contextPath)
    }

    @Throws(Exception::class)
    fun stop() {
        server.stop()
    }

    @Throws(Throwable::class)
    override fun before() {
        server.start()
    }

    override fun after() {
        try {
            server.stop()
        } catch (e: Exception) {
            throw RuntimeException("failed stopping server", e)
        }

    }

}
