package org.kvp_bld_sck.BookDatabase.dao.impl;

import org.kvp_bld_sck.BookDatabase.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookDaoException;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookDataFileNotFoundException;
import org.kvp_bld_sck.BookDatabase.dao.exception.BookNotFoundException;
import org.kvp_bld_sck.BookDatabase.entity.Book;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final String BOOK_DATA_FILE_PATH = "./resources/book.dat";
    private static final String ID_DATA_FILE_PATH = "./resource/bookid.dat";

    private RandomAccessFile getBookDataFile() throws BookDaoException {
        File dataFile = new File(BOOK_DATA_FILE_PATH);
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new BookDataFileNotFoundException("cannot create book data file", e);
            }
        try {
            RandomAccessFile file = new RandomAccessFile(dataFile, "rw");
            return file;
        } catch (FileNotFoundException e) {
            throw new BookDataFileNotFoundException(" cannot open book data file", e);
        }
    }

    private int getNewId() throws BookDaoException {
        File idFile = new File(ID_DATA_FILE_PATH);
        if (!idFile.exists()) {
            try (PrintStream out = new PrintStream(new FileOutputStream(idFile))) {
                out.println(0);
            } catch (FileNotFoundException e) {
                throw new BookDaoException("cannot create file for ids data", e);
            }
            return 1;
        }
        try (RandomAccessFile file = new RandomAccessFile(idFile, "rw")) {
            long pos = file.getFilePointer();
            int id = 1 + Integer.parseInt(file.readLine());
            file.seek(pos);
            file.writeUTF(String.valueOf(id) + '\n');
            return id;
        } catch (FileNotFoundException e) {
            throw new BookDaoException("cannot open file for ids data", e);
        } catch (IOException e) {
            throw new BookDaoException("hz", e);
        }
    }

    //return positions in file
    private List<Long> find(RandomAccessFile file, Book pattern, boolean useLike) throws BookDaoException {
        List<Long> positions = new LinkedList<>();

        try {
            for (long pos = file.getFilePointer(); pos < file.length(); pos = file.getFilePointer()) {
                file.readLine();
                long id = Long.parseLong(file.readLine().trim());
                String author = file.readLine().trim();
                String title = file.readLine().trim();
                Date publicationDate = new Date(Long.parseLong(file.readLine().trim()));
                String location = file.readLine().trim();
                file.readLine();

                Book book = new Book(id, title, author, publicationDate, location);
                if ((useLike && book.like(pattern)) || (!useLike && book.equals(pattern)))
                    positions.add(pos);
            }
        } catch (IOException e) {
            throw new BookDaoException("cannot read from book data file", e);
        }

        return positions;
    }

    //return position
    //index = -1..inf
    //-1 - last
    private long get(RandomAccessFile file, int index) throws BookDaoException {
        long pos = 0;
        try {
            while ((file.getFilePointer() < file.length()) && (0 != index)) {
                pos = file.getFilePointer();
                file.readLine();
                file.readLine();
                file.readLine();
                file.readLine();
                file.readLine();
                file.readLine();
                file.readLine();
            }
        } catch (IOException e) {
            throw new BookDaoException("cannot read from book data file", e);
        }
        if (-1 == index)
            return pos;
        else if (0 != index)
            throw new BookDaoException("no such index");
        try {
            return file.getFilePointer();
        } catch (IOException e) {
            throw new BookDaoException("cannot get file position", e);
        }
    }

    private Book read(RandomAccessFile file) throws BookDaoException {
        try {
            file.readLine();
            long id = Long.parseLong(file.readLine().trim());
            String author = file.readLine().trim();
            String title = file.readLine().trim();
            Date publicationDate = new Date(Long.parseLong(file.readLine().trim()));
            String location = file.readLine().trim();
            file.readLine();

            return new Book(id, title, author, publicationDate, location);
        } catch (IOException e) {
            throw new BookDaoException("cannot read from books file", e);
        }
    }

    private void write(RandomAccessFile file, Book book) throws BookDaoException {
        try {
            file.writeUTF("{\n");
            file.writeUTF("\t" + book.getId() + "\n");
            file.writeUTF("\t" + book.getAuthor() + "\n");
            file.writeUTF("\t" + book.getTitle() + "\n");
            file.writeUTF("\t" + book.getPublicationDate().getTime() + "\n");
            file.writeUTF("\t" + book.getLocation() + "\n");
            file.writeUTF("}\n");
        } catch (IOException e) {
            throw new BookDaoException("cannot write to books file", e);
        }
    }


    @Override
    public long saveBook(Book book) throws BookDaoException {
        book.setId(-1);
        if (isBookExist(book))
            throw new BookDaoException(book.toString() + " exist");
        book.setId(getNewId());
        if (isBookExist(book))
            throw new BookDaoException("id " + book.getId() + " exist");

        try (RandomAccessFile file = getBookDataFile()) {
            file.seek(file.length());
            write(file, book);
        } catch (IOException e) {
            throw new BookDaoException("cannot seek to end of file", e);
        }
        return book.getId();
    }

    @Override
    public Book getBook(long id) throws BookDaoException {
        Book pattern = new Book(id);
        List<Book> res = getBooks(pattern);
        if (0 == res.size())
            throw new BookNotFoundException("book with id " + id + " not found");
        return res.get(0);
    }

    @Override
    public List<Book> getBooks(Book pattern) throws BookDaoException {
        List<Book> books = new LinkedList<>();
        try (RandomAccessFile file = getBookDataFile()) {
            List<Long> booksPos = find(file, pattern, true);
            for (long pos : booksPos){
                file.seek(pos);
                books.add(read(file));
            }
        } catch (IOException e) {
            throw new BookDaoException("cannot seek to position in file", e);
        }
        return books;
    }

    @Override
    public void updateBook(Book book) throws BookDaoException {
        try (RandomAccessFile file = getBookDataFile()) {
            List<Long> position = find(file, book, false);
            if (1 != position.size())
                throw new BookNotFoundException(book.toString() + " not found");
            file.seek(position.get(0));
            write(file, book);
        } catch (IOException e) {
            throw new BookDaoException("cannot close or seek in books data file", e);
        }
    }

    @Override
    public void deleteBook(Book book) throws BookDaoException {
        try (RandomAccessFile file = getBookDataFile()) {
            List<Long> position = find(file, book, false);
            if (1 != position.size())
                throw new BookNotFoundException(book.toString() + " not found");
            file.seek(position.get(0));
            long end = get(file, -1);
            file.seek(position.get(0));
            if (end != position.get(0)) {
                long posAfterDeletingBook = get(file, 1);
                file.seek(posAfterDeletingBook);
                byte[] buf = new byte[(int) (file.length() - posAfterDeletingBook)];
                file.readFully(buf);
                file.seek(position.get(0));
                file.write(buf);
                file.setLength(file.length() - posAfterDeletingBook + position.get(0));
            }

        } catch (IOException e) {
            throw new BookDaoException("cannot close or seek in books data file", e);
        }
    }

    @Override
    public boolean isBookExist(Book book) throws BookDaoException {
        try (RandomAccessFile file = getBookDataFile()) {
            return 0 != find(file, book, false).size();
        } catch (IOException e) {
            throw new BookDaoException("cannot close book data file", e);
        }
    }
}
