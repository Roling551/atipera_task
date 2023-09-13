package com.gmail.robertzylan.atiperaTask.configuration;

import com.gmail.robertzylan.atiperaTask.dto.ErrorResponse;
import com.gmail.robertzylan.atiperaTask.exceptions.HeaderValueNotAllowed;
import com.gmail.robertzylan.atiperaTask.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HeaderValueNotAllowed.class)
    public ResponseEntity<ErrorResponse> handleUndesiredHeaderException(HeaderValueNotAllowed exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

}