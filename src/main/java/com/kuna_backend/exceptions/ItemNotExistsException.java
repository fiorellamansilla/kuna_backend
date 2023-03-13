package com.kuna_backend.exceptions;

public class ItemNotExistsException extends IllegalArgumentException {
    public ItemNotExistsException(String msg) {
        super(msg);
    }
}
