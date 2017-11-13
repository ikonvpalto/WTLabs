package org.kvp_bld_sck.BookDatabase.controller.exception;

public class CannotExecuteCommandException extends ControllerException {
    public CannotExecuteCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotExecuteCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
