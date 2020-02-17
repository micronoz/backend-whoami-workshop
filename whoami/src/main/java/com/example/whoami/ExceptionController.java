package com.example.whoami;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = UserNameTakenException.class)
    public ResponseEntity<Object> userException(UserNameTakenException exception) {
        return new ResponseEntity<>("Username is not available.", HttpStatus.NOT_ACCEPTABLE);
    }
}