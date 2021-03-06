package com.nile.kmooc.http;

public class HttpResponseStatusException extends Exception {
    private final int statusCode;

    public HttpResponseStatusException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
