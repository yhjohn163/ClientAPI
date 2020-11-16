package com.jadan.client.exception;

public class InternalServerException extends RuntimeException {
//        public InternalServerException() {
//    }

    public InternalServerException(String message, Throwable e) {
        super(message, e);
    }
}
