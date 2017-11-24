package org.kvp_bld_sck.BookDatabase.client.transport.exception;

public class CannotConnectToServerException extends ClientException {
    public CannotConnectToServerException(String message) {
        super(message);
    }

    public CannotConnectToServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotConnectToServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
