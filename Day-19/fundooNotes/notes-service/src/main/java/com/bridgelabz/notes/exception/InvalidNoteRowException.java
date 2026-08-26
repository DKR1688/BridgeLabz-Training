package com.bridgelabz.notes.exception;

public class InvalidNoteRowException extends RuntimeException {

    private final int rowNumber;

    public InvalidNoteRowException(int rowNumber, String message) {
        super(String.format("Row %d: %s", rowNumber, message));
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
