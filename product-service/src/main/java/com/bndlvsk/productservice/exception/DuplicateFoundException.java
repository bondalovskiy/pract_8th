package com.bndlvsk.productservice.exception;

public class DuplicateFoundException extends RuntimeException{

    public DuplicateFoundException(String message) {
        super(message);
    }
}
