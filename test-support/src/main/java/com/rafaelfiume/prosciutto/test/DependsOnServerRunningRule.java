package com.rafaelfiume.prosciutto.test;

import org.junit.rules.ExternalResource;

public class DependsOnServerRunningRule extends ExternalResource {

    private final StubbedServer server = new StubbedServer();

    public void primeSuccessfulResponse(String contextPath, String response) {
        server.primeSuccessfulResponse(contextPath, response);
    }

    @Override
    protected void before() throws Throwable {
        server.start();
    }

    @Override
    protected void after() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("failed stopping server", e);
        }
    }

}
