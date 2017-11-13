package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.kvp_bld_sck.BookDatabase.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookDataFileNotFoundException;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final String BOOK_FILE = ".\\resources\\book.dat";
    private static final String BOOK_ID_FILE = ".\\resources\\bookIds.dat";

    private List<Book> books;
    private RandomAccessFile file;
    private boolean working;

    private void openBookDataFile() throws BookDaoException {
        File dataFile = new File(BOOK_FILE);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new BookDataFileNotFoundException("cannot create book data file", e);
            }
        try {
            file = new RandomAccessFile(dataFile, "rw");
        } catch (FileNotFoundException e) {
            throw new BookDaoException("cannot open book data file", e);
        }
    }

    private void write(Book book) throws BookDaoException {
        try {
            file.writeUTF("{\n");
            file.writeUTF("\t" + book.getId() + "\n");
            file.writeUTF("\t" + book.getAuthor() + "\n");
            file.writeUTF("\t" + book.getTitle() + "\n");
            file.writeUTF("\t" + book.getPublicationDate().getTime() + "\n");
            file.writeUTF("\t" + book.getLocation() + "\n");
            file.writeUTF("}\n");
        } catch (IOException e) {
            throw new BookDaoException("cannot write to book data file", e);
        }
    }

    private Book read() throws BookDaoException {
        try {
            file.readLine();
            long id = Long.parseLong(file.readLine().trim());
            String author = file.readLine().trim();
            String title = file.readLine().trim();
            Date publicationDate = new Date(Long.parseLong(file.readLine().trim()));
            String location = file.readLine().trim();
            file.readLine();

            return new Book(id, author, title, publicationDate, location);
        } catch (IOException e) {
            throw new BookDaoException("cannot write to book data file", e);
        }
    }

    private List<Book> load() throws BookDaoException {
        try {
            books = new LinkedList<>();
            while (file.getFilePointer() < file.length())
                books.add(read());
            return books;
        } catch (IOException e) {
            throw new BookDaoException("cannot close book data file", e);
        }
    }

    private void save() throws BookDaoException {
        try {
            file.setLength(0);
            for (Book book : books)
                write(book);
        } catch (IOException e) {
            throw new BookDaoException("cannot close or sizing book data file", e);
        }
    }

    private void startWorking() throws BookDaoException {
        if (!working) {
            openBookDataFile();
            load();
            working = true;
        }
    }

    private void endWorking() throws BookDaoException {
        if (working) {
            save();
            books = null;
            try {
                file.close();
            } catch (IOException e) {
                throw new BookDaoException("cannot close book data file", e);
            }
            working = false;
        }
    }

    private long generateId() throws BookDaoException {
        File idFile = new File(BOOK_ID_FILE);
        if (!idFile.exists())
            try {
                idFile.createNewFile();
            } catch (IOException e) {
                throw new BookDataFileNotFoundException("cannot create book ids data file", e);
            }
        try (RandomAccessFile file = new RandomAccessFile(idFile, "rw")) {
            long id = 1;
            if (file.getFilePointer() < file.length())
                id += file.readLong();
            file.seek(0);
            file.writeLong(id);
            return id;
        } catch (FileNotFoundException e) {
            throw new BookDaoException("cannot open book ids data file", e);
        } catch (IOException e) {
            throw new BookDaoException("cannot close or seek in book ids data file", e);
        }
    }
    
    @Override
    public long saveBook(Book book) throws BookDaoException {
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
    public Book getBook(long id) throws BookDaoException {
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
    public List<Book> getBooks(Book pattern) throws BookDaoException {
        try {
            startWorking();

            List<Book> result = new LinkedList<>();
            for (Book book : books)
                if (book.like(pattern))
                    result.add(book);
            return result;
        } finally {
            endWorking();
        }
    }

    @Override
    public void updateBook(Book book) throws BookDaoException {
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
    public void deleteBook(Book book) throws BookDaoException {
        try {
            startWorking();

            books.remove(book);
        } finally {
            endWorking();
        }
    }

    @Override
    public boolean isBookExist(Book book) throws BookDaoException {
        try {
            startWorking();

            return books.contains(book);
        } finally {
            endWorking();
        }
    }
}
