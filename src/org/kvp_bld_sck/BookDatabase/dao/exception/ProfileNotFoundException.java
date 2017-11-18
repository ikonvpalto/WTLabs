package org.kvp_bld_sck.BookDatabase.dao.exception;

public class ProfileNotFoundException extends ProfileDaoException {
    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
