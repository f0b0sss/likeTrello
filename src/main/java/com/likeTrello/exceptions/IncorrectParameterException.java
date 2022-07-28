package com.likeTrello.exceptions;

public class IncorrectParameterException extends RuntimeException{
    public IncorrectParameterException() {
        super();
    }

    public IncorrectParameterException(String message) {
        super(message);
    }
}
