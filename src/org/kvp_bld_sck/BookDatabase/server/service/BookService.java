package org.kvp_bld_sck.BookDatabase.server.service;

import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.server.service.exception.ServiceException;

import java.util.List;

public interface BookService {

    Book getBook(long id, UserSession userSession) throws ServiceException;
    List<Book> getBooks(Book pattern, UserSession userSession) throws ServiceException;

    /**
     * @return id of new book
     * @throws ServiceException
     */
    long addBook(Book book, UserSession userSession) throws ServiceException;
    void updateBook(Book book, UserSession userSession) throws ServiceException;
    void deleteBook(Book book, UserSession userSession) throws ServiceException;

}
