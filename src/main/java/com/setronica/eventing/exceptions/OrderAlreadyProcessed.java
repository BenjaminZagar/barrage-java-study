package com.setronica.eventing.exceptions;

public class OrderAlreadyProcessed extends BaseApplicationException {
    public OrderAlreadyProcessed() {
    }

    public OrderAlreadyProcessed(String message) {
        super(message);
    }

    public OrderAlreadyProcessed(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderAlreadyProcessed(Throwable cause) {
        super(cause);
    }

    public OrderAlreadyProcessed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
