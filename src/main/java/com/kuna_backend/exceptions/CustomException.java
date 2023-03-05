package com.kuna_backend.exceptions;

public class CustomException extends IllegalArgumentException {
    public CustomException (String msg) {
        super(msg);
    }
}
