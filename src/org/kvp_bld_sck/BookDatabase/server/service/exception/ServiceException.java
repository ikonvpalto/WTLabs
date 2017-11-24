package org.kvp_bld_sck.BookDatabase.server.service.exception;

public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage(), cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message + ": " + cause.getMessage(), cause, enableSuppression, writableStackTrace);
    }
}
