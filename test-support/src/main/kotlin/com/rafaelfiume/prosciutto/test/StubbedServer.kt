package com.rafaelfiume.prosciutto.test

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.server.handler.HandlerCollection

import java.io.IOException

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import java.lang.String.format
import javax.servlet.http.HttpServletResponse.SC_OK

class StubbedServer {

    private val server: Server
    private val handlerCollection = HandlerCollection(true)

    init {
        this.server = Server(8081)
        this.server.handler = handlerCollection
    }

    @Throws(Exception::class)
    fun start() {
        server.start()
    }

    @Throws(Exception::class)
    fun stop() {
        server.stop()
    }

    fun primeSuccessfulResponse(contextPath: String, response: String) {
        addHandler(contextPath, XmlResponseHandler(response))
    }

    fun primeServerErrorWhenRequesting(contextPath: String) {
        addHandler(contextPath, ThrowsExceptionHandler())
    }

    private fun addHandler(contextPath: String, handler: AbstractHandler) {
        val context = ContextHandler()
        context.contextPath = contextPath
        context.classLoader = Thread.currentThread().contextClassLoader
        context.handler = handler
        this.handlerCollection.addHandler(context)
        try {
            context.start()
        } catch (e: Exception) {
            throw RuntimeException(format("failed to prime %s", contextPath), e)
        }
    }

    internal class XmlResponseHandler(private val responseBody: String) : AbstractHandler() {

        @Throws(IOException::class, ServletException::class)
        override fun handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {
            response.contentType = "application/xml;charset=utf-8"
            response.status = SC_OK
            baseRequest.isHandled = true
            response.writer.append(responseBody)
        }
    }

    internal class ThrowsExceptionHandler : AbstractHandler() {

        @Throws(IOException::class, ServletException::class)
        override fun handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) {
            throw RuntimeException("primed for throwing exception... don't blame the messenger ;)")
        }
    }

}