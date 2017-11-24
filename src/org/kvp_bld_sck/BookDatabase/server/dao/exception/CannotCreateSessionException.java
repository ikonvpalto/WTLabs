package org.kvp_bld_sck.BookDatabase.server.dao.exception;

public class CannotCreateSessionException extends SessionDaoException {
    public CannotCreateSessionException(String message) {
        super(message);
    }

    public CannotCreateSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
