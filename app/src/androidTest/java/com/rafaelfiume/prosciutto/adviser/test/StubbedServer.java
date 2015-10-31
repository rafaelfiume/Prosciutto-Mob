package com.rafaelfiume.prosciutto.adviser.test;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    private static String supplierAdviceResponse() {
        return readFile("com.rafaelfiume.salume.advisor.SalumeAdvisorHappyPathTest_Salume_advice_response_from_Supplier_to_Customer.txt");
    }

    private static String readFile(String resName) {
        try (final InputStream is = AdviserEndToEndTest.class.getClassLoader().getResourceAsStream(resName)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}