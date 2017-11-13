package org.kvp_bld_sck.BookDatabase.service;

import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.entity.Session;
import org.kvp_bld_sck.BookDatabase.service.exception.ServiceException;

import java.util.List;

public interface BookService {

    Book getBook(long id, Session session) throws ServiceException;
    List<Book> getBooks(Book pattern, Session session) throws ServiceException;

    /**
     * @return id of new book
     * @throws ServiceException
     */
    long addBook(Book book, Session session) throws ServiceException;
    void updateBook(Book book, Session session) throws ServiceException;
    void deleteBook(Book book, Session session) throws ServiceException;

}
