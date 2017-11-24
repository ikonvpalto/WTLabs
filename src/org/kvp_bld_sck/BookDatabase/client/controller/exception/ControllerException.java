package org.kvp_bld_sck.BookDatabase.client.controller.exception;

public class ControllerException extends Exception {

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage(), cause);
    }

    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message + ": " + cause.getMessage(), cause, enableSuppression, writableStackTrace);
    }
}
