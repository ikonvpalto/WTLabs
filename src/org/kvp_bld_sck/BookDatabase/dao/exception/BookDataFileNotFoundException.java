package org.kvp_bld_sck.BookDatabase.dao.exception;

public class BookDataFileNotFoundException extends BookDaoException {
    public BookDataFileNotFoundException(String message) {
        super(message);
    }

    public BookDataFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookDataFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
