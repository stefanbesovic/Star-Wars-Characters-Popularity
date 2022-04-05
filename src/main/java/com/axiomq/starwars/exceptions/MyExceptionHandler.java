package com.axiomq.starwars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails NoSuchElementHandler(NoSuchElementException exception,
                                             HttpServletRequest request) {

        return ErrorDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDetails NoSuchElementHandler(AccessDeniedException exception,
                                             HttpServletRequest request) {

        return ErrorDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message(exception.getMessage())
                .validationErorr(Collections.singletonMap("method", "Method is not allowed."))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails validationErrorHandler(MethodArgumentNotValidException exception,
                                               HttpServletRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message("Validation Error")
                .build();

        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        bindingResult.getAllErrors().forEach((er) -> {
            String field = ((FieldError) er).getField();
            errors.put(field, er.getDefaultMessage());
        });

        error.setValidationErorr(errors);
        return error;
    }
}
