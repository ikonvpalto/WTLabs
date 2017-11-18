package org.kvp_bld_sck.BookDatabase.service.exception;

public class ProfileServiceException extends ServiceException {
    public ProfileServiceException(String message) {
        super(message);
    }

    public ProfileServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
