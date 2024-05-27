package com.example.dllab.common.exception;

public abstract class DanlabException extends RuntimeException {
    public DanlabException(String message) {
        super(message);
    }
}
