package com.likeTrello.exceptions;

public class ColumnNotFoundException extends RuntimeException{
    public ColumnNotFoundException() {
        super();
    }
    public ColumnNotFoundException(String message) {
        super(message);
    }
}
