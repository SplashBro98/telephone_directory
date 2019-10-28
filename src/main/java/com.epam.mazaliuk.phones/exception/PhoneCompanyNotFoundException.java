package com.epam.mazaliuk.phones.exception;

public class PhoneCompanyNotFoundException extends EntityNotFoundException {

    public PhoneCompanyNotFoundException() {
        super();
    }

    public PhoneCompanyNotFoundException(String message) {
        super(message);
    }

    public PhoneCompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneCompanyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PhoneCompanyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
