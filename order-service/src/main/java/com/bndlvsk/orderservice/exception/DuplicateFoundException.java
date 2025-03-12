package com.bndlvsk.orderservice.exception;

public class DuplicateFoundException extends RuntimeException{

    public DuplicateFoundException(String message) {
        super(message);
    }
}
