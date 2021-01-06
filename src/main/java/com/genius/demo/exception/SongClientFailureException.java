package com.genius.demo.exception;

public class SongClientFailureException extends Exception {

    public SongClientFailureException(String message) {
        super(message);
    }

    public SongClientFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
