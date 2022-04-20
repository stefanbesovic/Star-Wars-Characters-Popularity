package com.axiomq.starwars.exceptions;

public class ImageUploadException extends RuntimeException{
    public ImageUploadException(String errorMessage) { super(errorMessage); }
}
