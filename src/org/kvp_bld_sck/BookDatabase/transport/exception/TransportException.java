package org.kvp_bld_sck.BookDatabase.transport.exception;

public class TransportException extends Exception {

    public TransportException(String message) {
        super(message);
    }

    public TransportException(String message, Throwable cause) {
        super(message + ":" + cause.getMessage(), cause);
    }

    public TransportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message + ":" + cause.getMessage(), cause, enableSuppression, writableStackTrace);
    }
}
