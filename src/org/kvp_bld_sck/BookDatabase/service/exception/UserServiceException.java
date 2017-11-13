package org.kvp_bld_sck.BookDatabase.service.exception;

public class UserServiceException extends ServiceException {

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserServiceException(String message) {
        super(message);
    }
}
