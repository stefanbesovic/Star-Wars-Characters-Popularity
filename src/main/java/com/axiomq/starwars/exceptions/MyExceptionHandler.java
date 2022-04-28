package com.axiomq.starwars.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails ObjectNotFoundHandler(ObjectNotFoundException exception,
                                             HttpServletRequest request) {

        log.error("Object not found exception has occurred.", exception);
        return ErrorDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails NoSuchElementHandler(NotAuthorizedException exception,
                                             HttpServletRequest request) {
        log.error("No such element exception has occurred.", exception);

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
        log.error("Validation error exception has occurred.", exception);

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

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails NoSuchElementHandler(ImageUploadException exception,
                                             HttpServletRequest request) {
        log.error("Unable to read inputStream for image.", exception);

        return ErrorDetails.builder()
                .path(request.getServletPath())
                .timestamp(new Timestamp(new Date().getTime()))
                .message(exception.getMessage())
                .validationErorr(Collections.singletonMap("error", "Can't find image."))
                .build();
    }
}
