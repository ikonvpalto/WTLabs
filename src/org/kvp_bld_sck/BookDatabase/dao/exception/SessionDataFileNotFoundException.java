package org.kvp_bld_sck.BookDatabase.dao.exception;

public class SessionDataFileNotFoundException extends SessionDaoException {
    public SessionDataFileNotFoundException(String message) {
        super(message);
    }

    public SessionDataFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionDataFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
