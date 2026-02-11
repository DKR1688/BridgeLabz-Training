package com.healthclinic.exception;
// exception to validate uniqueness to register new patient
public class DuplicatePatientException extends Exception {
    public DuplicatePatientException(String msg) {
        super(msg);
    }
}