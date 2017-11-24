package org.kvp_bld_sck.BookDatabase.server.dao.exception;

public class BookDaoException extends DaoException {
    public BookDaoException(String message) {
        super(message);
    }

    public BookDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
