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

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
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
        if ((null == obj) || !(obj instanceof Book))
            return false;
        Book other = (Book) obj;
        if ((id != -1) && (other.id != -1))
            return id == other.id;
        boolean authorsEquals = ((null != author) && (null != other.getAuthor())) && (author.equals(other.getAuthor())),
                titlesEquals = ((null != title) && (null != other.getTitle())) && (title.equals(other.getTitle())),
                datesEquals = ((null != publicationDate) && (null != other.getPublicationDate())) && (publicationDate.equals(other.getPublicationDate()));
        return authorsEquals && titlesEquals && datesEquals;
    }

}
