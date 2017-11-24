package org.kvp_bld_sck.BookDatabase.client.controller.exception;

public class UserDataNotValidException extends ControllerException {

    public UserDataNotValidException(String message) {
        super(message);
    }

    public UserDataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDataNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
