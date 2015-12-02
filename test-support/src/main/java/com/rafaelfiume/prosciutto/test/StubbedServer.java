package com.rafaelfiume.prosciutto.test;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rafaelfiume.prosciutto.test.SalumeApiContractExampleReader.supplierAdviceResponse;

public class StubbedServer {

    private final Server server;

    public StubbedServer() {
        this.server = new Server(8081);

        ContextHandler context = new ContextHandler();
        context.setContextPath("/salume/supplier/advise/for/Expert");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);

        context.setHandler(new HelloHandler());
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public class HelloHandler extends AbstractHandler {
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
            response.setContentType("application/xml;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().append(supplierAdviceResponse());
        }
    }


}