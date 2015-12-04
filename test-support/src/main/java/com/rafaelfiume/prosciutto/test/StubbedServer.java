package com.rafaelfiume.prosciutto.test;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class StubbedServer {

    private final Server server;

    private final HandlerCollection handlerCollection = new HandlerCollection(true);

    public StubbedServer() {
        this.server = new Server(8081);
        this.server.setHandler(handlerCollection);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void primeSuccessfulResponse(String contextPath, String response) {
        final ContextHandler context = new ContextHandler();
        context.setContextPath(contextPath);
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        context.setHandler(new HelloHandler(response));
        this.handlerCollection.addHandler(context);
        try {
            context.start();
        } catch (Exception e) {
            throw new RuntimeException(format("failed to prime %s", contextPath), e);
        }
    }

    static class HelloHandler extends AbstractHandler {
        private final String responseBody;

        public HelloHandler(String responseBody) {
            this.responseBody = responseBody;
        }

        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setContentType("application/xml;charset=utf-8");
            response.setStatus(SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().append(responseBody);
        }
    }


}