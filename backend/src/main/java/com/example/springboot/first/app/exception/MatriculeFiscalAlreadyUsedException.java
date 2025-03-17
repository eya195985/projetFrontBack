package com.example.springboot.first.app.exception;

public class MatriculeFiscalAlreadyUsedException extends RuntimeException {
    public MatriculeFiscalAlreadyUsedException(String message) {
        super(message);
    }
}

