package com.likeTrello.exceptions;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
