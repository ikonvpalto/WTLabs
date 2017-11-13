package org.kvp_bld_sck.BookDatabase.dao;

import org.kvp_bld_sck.BookDatabase.dao.exception.BookDaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;

import java.util.List;

public interface BookDao {

    long saveBook(Book book) throws BookDaoException;
    Book getBook(long id) throws BookDaoException;
    List<Book> getBooks(Book pattern) throws BookDaoException;
    void updateBook(Book book) throws BookDaoException;
    void deleteBook(Book book) throws BookDaoException;
    boolean isBookExist(Book book) throws BookDaoException;

}
