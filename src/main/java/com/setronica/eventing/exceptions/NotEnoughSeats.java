package com.setronica.eventing.exceptions;

public class NotEnoughSeats extends BaseApplicationException {
    public NotEnoughSeats() {
    }

    public NotEnoughSeats(String message) {
        super(message);
    }

    public NotEnoughSeats(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSeats(Throwable cause) {
        super(cause);
    }

    public NotEnoughSeats(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
