package org.kvp_bld_sck.BookDatabase.server.transport.exception;

public class CannotExecuteException extends ServerException {
    public CannotExecuteException(String message) {
        super(message);
    }

    public CannotExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
