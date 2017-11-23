package org.kvp_bld_sck.BookDatabase.service.impl;

import org.kvp_bld_sck.BookDatabase.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.service.BookService;
import org.kvp_bld_sck.BookDatabase.service.exception.BookServiceException;
import org.kvp_bld_sck.BookDatabase.service.exception.InvalidDataException;
import org.kvp_bld_sck.BookDatabase.service.exception.PermissionDeniedException;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public BookServiceImpl() {
        bookDao = DaoFactory.getFactory().getBookDao();
    }

    @Override
    public Book getBook(long id, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("get", userSession))
            throw new PermissionDeniedException("cannot get book: permission denied");

        if (id < 1)
            throw new InvalidDataException("book id not correct");

        try {
            return bookDao.getBook(id);
        } catch (DaoException e) {
            throw new BookServiceException("cannot get book " + id, e);
        }
    }

    @Override
    public List<Book> getBooks(Book pattern, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("get", userSession))
            throw new PermissionDeniedException("cannot get books: permission denied");

        if (null == pattern)
            throw new InvalidDataException("book pattern not correct");

        try {
            return bookDao.getBooks(pattern);
        } catch (DaoException e) {
            throw new BookServiceException("cannot get book by pattern " + pattern, e);
        }
    }

    @Override
    public long addBook(Book book, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("add", userSession))
            throw new PermissionDeniedException("cannot add book: permission denied");

        if ((null == book)
                || (null == book.getAuthor())
                || (null == book.getTitle())
                || (null == book.getPublicationDate())
                || (null == book.getLocation()))
            throw new InvalidDataException("book data not correct");

        try {
            return bookDao.saveBook(book);
        } catch (DaoException e) {
            throw new BookServiceException("cannot add " + book, e);
        }
    }

    @Override
    public void updateBook(Book book, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("update", userSession))
            throw new PermissionDeniedException("cannot update book: permission denied");

        if ((null == book)
                || (null == book.getAuthor())
                || (null == book.getTitle())
                || (null == book.getPublicationDate())
                || (null == book.getLocation())
                || (1 > book.getId()))
            throw new InvalidDataException("book data not correct");

        try {
            bookDao.updateBook(book);
        } catch (DaoException e) {
            throw new BookServiceException("cannot update " + book, e);
        }
    }

    @Override
    public void deleteBook(Book book, UserSession userSession) throws ServiceException {
        if (!PermissionChecker.getChecker().checkMethod("delete", userSession))
            throw new PermissionDeniedException("cannot delete book: permission denied");

        if ((null == book) || (1 > book.getId()))
            throw new InvalidDataException("book data not correct");

        try {
            bookDao.deleteBook(book);
        } catch (DaoException e) {
            throw new BookServiceException("cannot delete " + book, e);
        }
    }
}
