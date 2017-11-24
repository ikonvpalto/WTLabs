package org.kvp_bld_sck.BookDatabase.server.dao.impl.rawfiles;

import org.kvp_bld_sck.BookDatabase.server.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.BookDaoException;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.BookNotFoundException;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BookDaoImpl implements BookDao {

    private static final String BOOK_FILE = ".\\resources\\book.dat";
    private static final String BOOK_ID_FILE = ".\\resources\\bookIds.dat";

    private List<Book> books;
    private boolean working;


    private boolean like(Book pattern, Book book) {
        boolean isTitleLike = ((null != pattern.getTitle())
                            && (null != book.getTitle())
                            && Pattern.compile(pattern.getTitle()).matcher(book.getTitle()).matches());
        boolean isAuthorLike = ((null != pattern.getAuthor())
                             && (null != book.getAuthor())
                             && Pattern.compile(pattern.getAuthor()).matcher(book.getAuthor()).matches());
        return isAuthorLike && isTitleLike;
    }
    
    private void write(PrintStream out, Book book) throws DaoException {
        try {
            out.println("{");
            out.println("\t" + book.getId());
            out.println("\t" + book.getAuthor());
            out.println("\t" + book.getTitle());
            out.println("\t" + book.getPublicationDate().getTime());
            out.println("\t" + book.getLocation());
            out.println("}");
        } catch (Exception e) {
            throw new BookDaoException("cannot write to book data file", e);
        }
    }

    private Book read(Scanner in) throws DaoException {
        try {
            in.nextLine();
            long id = Long.parseLong(in.nextLine().trim());
            String author = in.nextLine().trim();
            String title = in.nextLine().trim();
            Date publicationDate = new Date(Long.parseLong(in.nextLine().trim()));
            String location = in.nextLine().trim();
            in.nextLine();

            return new Book(id, author, title, publicationDate, location);
        } catch (Exception e) {
            throw new BookDaoException("cannot read from book data file", e);
        }
    }

    private void load() throws DaoException {
        try(Scanner in = FileUtils.getIn(BOOK_FILE)) {
            books = new LinkedList<>();
            while (in.hasNextLine())
                books.add(read(in));
        }
    }

    private void save() throws DaoException {
        try(PrintStream out = FileUtils.getOut(BOOK_FILE)) {
            for (Book book : books)
                write(out, book);
        }
    }

    private void startWorking() throws DaoException {
        if (!working) {
            load();
            working = true;
        }
    }

    private void endWorking() throws DaoException {
        if (working) {
            save();
            books = null;
            working = false;
        }
    }

    private long generateId() throws DaoException {
        long id = 1;
        try (Scanner in = FileUtils.getIn(BOOK_ID_FILE)) {
            if (in.hasNextLong())
                id += in.nextLong();
        }
        try (PrintStream out = FileUtils.getOut(BOOK_ID_FILE)) {
            out.println(id);
        }
        return id;
    }
    
    @Override
    public long saveBook(Book book) throws DaoException {
        try {
            startWorking();
            book.setId(generateId());
            books.add(book);
            return book.getId();
        } finally {
            endWorking();
        }
    }

    @Override
    public Book getBook(long id) throws DaoException {
        try {
            startWorking();

            for (Book book : books)
                if (book.getId() == id)
                    return book;
        } finally {
            endWorking();
        }
        throw new BookNotFoundException("book with id " + id + " not found");
    }

    @Override
    public List<Book> getBooks(Book pattern) throws DaoException {
        try {
            startWorking();

            List<Book> result = new LinkedList<>();
            for (Book book : books)
                if (like(pattern, book))
                    result.add(book);
            return result;
        } finally {
            endWorking();
        }
    }

    @Override
    public void updateBook(Book book) throws DaoException {
        try {
            startWorking();

            for (Book bok : books)
                if (bok.getId() == book.getId()) {
                    bok.setAuthor(book.getAuthor());
                    bok.setTitle(book.getTitle());
                    bok.setPublicationDate(book.getPublicationDate());
                    bok.setLocation(book.getLocation());
                    return;
                }
        } finally {
            endWorking();
        }
        throw new BookNotFoundException("book with id " + book.getId() + " not found");
    }

    @Override
    public void deleteBook(Book book) throws DaoException {
        try {
            startWorking();

            books.remove(book);
        } finally {
            endWorking();
        }
    }

    @Override
    public boolean isBookExist(Book book) throws DaoException {
        try {
            startWorking();

            return books.contains(book);
        } finally {
            endWorking();
        }
    }
}
