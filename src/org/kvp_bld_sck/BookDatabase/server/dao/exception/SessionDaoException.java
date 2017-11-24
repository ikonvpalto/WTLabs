package org.kvp_bld_sck.BookDatabase.server.dao.exception;

public class SessionDaoException extends DaoException {
    public SessionDaoException(String message) {
        super(message);
    }

    public SessionDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
