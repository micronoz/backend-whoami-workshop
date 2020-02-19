package com.example.whoami;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = UserNameTakenException.class)
    public ResponseEntity<Object> userException(UserNameTakenException exception) {
        return new ResponseEntity<>("Username is not available.\n", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> authException(UnauthorizedException exception) {
        return new ResponseEntity<>("Unauthorized operation.\n", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = IllegalGameStateException.class)
    public ResponseEntity<Object> gameStateException(IllegalGameStateException exception) {
        return new ResponseEntity<>(exception.getMessage() + "\n", HttpStatus.FORBIDDEN);
    }
}