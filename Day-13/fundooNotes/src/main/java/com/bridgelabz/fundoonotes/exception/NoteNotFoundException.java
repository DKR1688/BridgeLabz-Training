package com.bridgelabz.fundoonotes.exception;

public class NoteNotFoundException extends IllegalArgumentException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
