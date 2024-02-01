package com.setronica.eventing.exceptions;

public class ApplicationLogicException extends BaseApplicationException {
    public ApplicationLogicException() {
        super();
    }

    public ApplicationLogicException(String message) {
        super(message);
    }

    public ApplicationLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationLogicException(Throwable cause) {
        super(cause);
    }

    public ApplicationLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
