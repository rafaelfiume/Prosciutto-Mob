package com.rafaelfiume.prosciutto.adviser.integration;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
