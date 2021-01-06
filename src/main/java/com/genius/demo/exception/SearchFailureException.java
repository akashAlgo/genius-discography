package com.genius.demo.exception;

public class SearchFailureException extends Exception {

    public SearchFailureException(String message) {
        super(message);
    }

    public SearchFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
