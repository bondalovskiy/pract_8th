package com.bndlvsk.userservice.exceprion;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
