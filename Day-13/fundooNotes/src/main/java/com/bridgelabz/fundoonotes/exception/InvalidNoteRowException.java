package com.bridgelabz.fundoonotes.exception;

public class InvalidNoteRowException extends RuntimeException {
    private final int rowNumber;

    public InvalidNoteRowException(String message) {
        super(message);
        this.rowNumber = -1;
    }

    public InvalidNoteRowException(int rowNumber, String message) {
        super("Row " + rowNumber + ": " + message);
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
