package org.kvp_bld_sck.BookDatabase.server.dao.exception;

public class UserDaoException extends DaoException {
    public UserDaoException(String message) {
        super(message);
    }

    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
