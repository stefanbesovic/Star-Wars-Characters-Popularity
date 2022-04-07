package com.axiomq.starwars.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
