package org.kvp_bld_sck.BookDatabase.client.controller.exception;

public class SessionNotOpenException extends ControllerException {

    public SessionNotOpenException(String message) {
        super(message);
    }

    public SessionNotOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotOpenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
