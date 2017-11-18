package org.kvp_bld_sck.BookDatabase.dao.exception;

public class ProfileDataFileNotFound extends ProfileDaoException {
    public ProfileDataFileNotFound(String message) {
        super(message);
    }

    public ProfileDataFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileDataFileNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
