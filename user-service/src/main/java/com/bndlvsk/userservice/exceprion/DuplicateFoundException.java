package com.bndlvsk.userservice.exceprion;

public class DuplicateFoundException extends RuntimeException{

    public DuplicateFoundException(String message) {
        super(message);
    }
}
