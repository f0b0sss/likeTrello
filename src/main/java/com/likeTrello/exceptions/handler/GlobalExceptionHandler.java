package com.likeTrello.exceptions.handler;

import com.likeTrello.exceptions.BoardNotFoundException;
import com.likeTrello.exceptions.ColumnNotFoundException;
import com.likeTrello.exceptions.IncorrectParameterException;
import com.likeTrello.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IncorrectParameterException.class)
    public ResponseEntity invalidParameterException(IncorrectParameterException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity taskNotFoundException(TaskNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ColumnNotFoundException.class)
    public ResponseEntity columnNotFoundException(ColumnNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BoardNotFoundException.class)
    public ResponseEntity boardNotFoundException(BoardNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors =  new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(error -> {errors.put(error.getField(), error.getDefaultMessage());
                });

        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }


}
