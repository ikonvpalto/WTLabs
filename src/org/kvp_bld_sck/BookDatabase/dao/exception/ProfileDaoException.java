package org.kvp_bld_sck.BookDatabase.dao.exception;

public class ProfileDaoException extends DaoException {
    public ProfileDaoException(String message) {
        super(message);
    }

    public ProfileDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
