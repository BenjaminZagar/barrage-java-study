package com.setronica.eventing.exceptions;

public class BaseApplicationException extends RuntimeException {
    public BaseApplicationException() {
    }

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseApplicationException(Throwable cause) {
        super(cause);
    }

    public BaseApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
