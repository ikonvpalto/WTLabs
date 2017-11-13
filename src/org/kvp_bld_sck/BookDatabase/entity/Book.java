package org.kvp_bld_sck.BookDatabase.entity;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Book implements Serializable{

    private long id = -1;
    private String title;
    private String author;
    private Date publicationDate;
    private String location;

    public Book() {}

    public Book(long id) {
        this.id = id;
    }

    public Book(long id, String title, String author, Date publicationDate, String location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.location = location;
    }

    public Book(String title, String author, Date publicationDate) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Book(String title, String author, Date publicationDate, String location) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isElectronic() {
        return new File(location).exists();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + SimpleDateFormat.getDateInstance().format(publicationDate) +
                (isElectronic()? (", e-book") : (", paper book")) +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book))
            return false;
        if ((id != -1) && (((Book) obj).id != -1))
            return id == ((Book) obj).id;
        return (author.equals(((Book) obj).author))
                && (title.equals(((Book) obj).title))
                && (publicationDate.equals(((Book) obj).publicationDate));
    }

    public boolean like(Book pattern) {
        boolean isTitleLike = ((null == pattern.title) || Pattern.compile(pattern.title).matcher(title).find());
        boolean isAuthorLike = ((null == pattern.author) || Pattern.compile(pattern.author).matcher(author).find());
        boolean isDateLike;
        if (null == pattern.publicationDate)
            isDateLike = true;
        else {
            long year = (long) (1000 * 60 * 60 * 24 * 365.25);
            isDateLike = (pattern.publicationDate.getTime() - 2 * year <= publicationDate.getTime())
                        && (pattern.publicationDate.getTime() + 2 * year >= publicationDate.getTime());
        }
        return isAuthorLike && isDateLike && isTitleLike;
    }
}
