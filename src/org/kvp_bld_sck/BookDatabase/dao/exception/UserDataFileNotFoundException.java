package org.kvp_bld_sck.BookDatabase.dao.exception;

public class UserDataFileNotFoundException extends UserDaoException {
    public UserDataFileNotFoundException(String message) {
        super(message);
    }

    public UserDataFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDataFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
