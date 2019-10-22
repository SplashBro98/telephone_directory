package com.epam.mazaliuk.phones.exception;

public class PhoneCompanyException extends BaseException {

    public PhoneCompanyException() {
        super();
    }

    public PhoneCompanyException(String message) {
        super(message);
    }

    public PhoneCompanyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneCompanyException(Throwable cause) {
        super(cause);
    }

    protected PhoneCompanyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
