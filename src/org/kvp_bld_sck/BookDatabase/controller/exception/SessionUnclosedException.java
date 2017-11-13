package org.kvp_bld_sck.BookDatabase.controller.exception;

public class SessionUnclosedException extends ControllerException {

    public SessionUnclosedException(String message) {
        super(message);
    }

    public SessionUnclosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionUnclosedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
