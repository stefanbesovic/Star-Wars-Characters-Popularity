package com.axiomq.starwars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class NoSuchElementExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ElementNotFoundDetails NoSuchElementHandler(NoSuchElementException exception,
                                                       HttpServletRequest request) {
        ElementNotFoundDetails error = ElementNotFoundDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message(exception.getMessage())
                .build();

        return error;
    }
}
