package org.kvp_bld_sck.BookDatabase.service.impl;

import org.kvp_bld_sck.BookDatabase.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.dao.DaoFactory;
import org.kvp_bld_sck.BookDatabase.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.service.BookService;
import org.kvp_bld_sck.BookDatabase.service.ServiceFabric;
import org.kvp_bld_sck.BookDatabase.service.exception.BookServiceException;
import org.kvp_bld_sck.BookDatabase.service.exception.InvalidDataException;
import org.kvp_bld_sck.BookDatabase.service.exception.PermissionDeniedException;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.List;
import java.util.Set;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private SessionDao sessionDao;
    private UserDao userDao;

    public BookServiceImpl() {
        bookDao = DaoFactory.getFactory().getBookDao();
        userDao = DaoFactory.getFactory().getUserDao();
        sessionDao = DaoFactory.getFactory().getSessionDao();
    }

    private boolean checkPermissions(Session session, Set<User.UserGroup> accept) throws ServiceException {
        if ((null == session) || (null == session.getId()))
            throw new InvalidDataException("session id not correct");
        User user;
        try {
            user = userDao.getUser(sessionDao.getUser(session).getId());
        } catch (DaoException e) {
            throw new BookServiceException("cannot get user by session " + session.getId());
        }
        return accept.contains(user.getUserGroup());
    }

    @Override
    public Book getBook(long id, Session session) throws ServiceException {
        if (!checkPermissions(session, Set.of(User.UserGroup.ADMINISTRATOR, User.UserGroup.USER)))
            throw new PermissionDeniedException("cannot get book");

        if (id < 1)
            throw new InvalidDataException("book id not correct");

        try {
            return bookDao.getBook(id);
        } catch (DaoException e) {
            throw new BookServiceException("cannot get book " + id, e);
        }
    }

    @Override
    public List<Book> getBooks(Book pattern, Session session) throws ServiceException {
        if (!checkPermissions(session, Set.of(User.UserGroup.ADMINISTRATOR, User.UserGroup.USER)))
            throw new PermissionDeniedException("cannot get books");

        if (null == pattern)
            throw new InvalidDataException("book pattern not correct");

        try {
            return bookDao.getBooks(pattern);
        } catch (DaoException e) {
            throw new BookServiceException("cannot get book by pattern " + pattern, e);
        }
    }

    @Override
    public long addBook(Book book, Session session) throws ServiceException {
        if (!checkPermissions(session, Set.of(User.UserGroup.ADMINISTRATOR)))
            throw new PermissionDeniedException("cannot add book");

        if ((null == book)
                || (null == book.getAuthor())
                || (null == book.getTitle())
                || (null == book.getPublicationDate())
                || (null == book.getLocation()))
            throw new InvalidDataException("book data not correct");

        try {
            long id = bookDao.saveBook(book);
            return id;
        } catch (DaoException e) {
            throw new BookServiceException("cannot add " + book, e);
        }
    }

    @Override
    public void updateBook(Book book, Session session) throws ServiceException {
        if (!checkPermissions(session, Set.of(User.UserGroup.ADMINISTRATOR)))
            throw new PermissionDeniedException("cannot update book");

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
    public void deleteBook(Book book, Session session) throws ServiceException {
        if (!checkPermissions(session, Set.of(User.UserGroup.ADMINISTRATOR)))
            throw new PermissionDeniedException("cannot delete book");

        if ((null == book) || (1 > book.getId()))
            throw new InvalidDataException("book data not correct");

        try {
            bookDao.deleteBook(book);
        } catch (DaoException e) {
            throw new BookServiceException("cannot delete " + book, e);
        }
    }
}
