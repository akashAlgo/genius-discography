package com.genius.demo.exception.handler;

import com.genius.demo.exception.SearchFailureException;
import com.genius.demo.exception.SongClientFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class AccountControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SearchFailureException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            SearchFailureException ex) {

        ApiError apiError = ApiError.builder()
                .status(NOT_FOUND)
                .message(ex.getMessage())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(SongClientFailureException.class)
    protected ResponseEntity<Object> handleSongClientFailure(
            SearchFailureException ex) {

        ApiError apiError = ApiError.builder()
                .status(BAD_GATEWAY)
                .message(ex.getMessage())
                .build();

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
