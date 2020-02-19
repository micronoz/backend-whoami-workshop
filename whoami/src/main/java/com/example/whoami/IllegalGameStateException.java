package com.example.whoami;

public class IllegalGameStateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalGameStateException(String message) {
        super(message);
    }
}