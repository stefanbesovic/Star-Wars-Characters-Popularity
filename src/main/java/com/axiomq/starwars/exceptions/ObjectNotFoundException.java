package com.axiomq.starwars.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
