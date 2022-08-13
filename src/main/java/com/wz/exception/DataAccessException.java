package com.wz.exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String s) {
        super(s);
    }

    public DataAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataAccessException(Throwable throwable) {
        super(throwable);
    }
}
