package com.wz.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String s) {
        super(s);
    }

    public InvalidInputException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidInputException(Throwable throwable) {
        super(throwable);
    }
}
