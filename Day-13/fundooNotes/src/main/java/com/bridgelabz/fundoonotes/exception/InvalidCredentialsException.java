package com.bridgelabz.fundoonotes.exception;

public class InvalidCredentialsException extends IllegalArgumentException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
