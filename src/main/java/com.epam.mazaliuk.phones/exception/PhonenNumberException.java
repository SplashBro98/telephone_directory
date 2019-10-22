package com.epam.mazaliuk.phones.exception;

public class PhonenNumberException extends BaseException {

    public PhonenNumberException() {
        super();
    }

    public PhonenNumberException(String message) {
        super(message);
    }

    public PhonenNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhonenNumberException(Throwable cause) {
        super(cause);
    }

    protected PhonenNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
