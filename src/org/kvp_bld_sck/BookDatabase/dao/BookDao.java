package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;

import java.util.List;

public interface BookDao {

    long saveBook(Book book) throws DaoException;
    Book getBook(long id) throws DaoException;
    List<Book> getBooks(Book pattern) throws DaoException;
    void updateBook(Book book) throws DaoException;
    void deleteBook(Book book) throws DaoException;
    boolean isBookExist(Book book) throws DaoException;

}
