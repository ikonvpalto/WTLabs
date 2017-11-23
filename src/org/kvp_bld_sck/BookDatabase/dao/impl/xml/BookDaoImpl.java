package org.kvp_bld_sck.BookDatabase.dao.impl.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kvp_bld_sck.BookDatabase.dao.BookDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Book;
import org.kvp_bld_sck.BookDatabase.entitymanager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    private static final String DATABASE_PATH = "./resources/books.xml";

    private Map<String, Object> getAttributesMap(EntityManager<Book> bookManager) {
        return bookManager.getFieldsStream()
                          .filter(field -> null != field.getValue())
                          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Book parseXmlElement(Element element) {
        EntityManager<Book> bookManager = new EntityManager<>(new Book());
        bookManager.getFieldNamesStream().forEach(s -> bookManager.suppressedSet(s, element.attribute(s).getData()));
        return bookManager.getEntity();
    }

    private long allocateNewId(XmlDocumentSession session) {
        long id = (long) session.getResource().getRootElement().attribute("id").getData();
        session.getResource().getRootElement().attribute("id").setData(id + 1);
        return id;
    }

    private boolean isDocumentValid(Document document) {
        return (null != document)
                && (null != document.getRootElement())
                && (null != document.getRootElement().attribute("max_id"))
                && (Pattern.compile("^\\d+$").matcher(document.getRootElement().attribute("max_id").getValue()).matches())
                && (null != document.getRootElement().element("books list"));
    }

    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("book_db");
        Attribute id = DocumentHelper.createAttribute(root, "id", String.valueOf(0));
        Element entities = DocumentHelper.createElement("books list");

        root.add(entities);
        document.setRootElement(root);
        return document;
    }

    private XmlDocumentSession openSession() {
        XmlDocumentSession session = null;

        try {
            session = XmlDocumentSession.openSession(DATABASE_PATH);
            if (!isDocumentValid(session.getResource()))
                session = null;
        } catch (Exception ignored) {}

        if (null == session){
            session = new XmlDocumentSession(createDocument(), DATABASE_PATH);
        }

        return session;
    }

    @Override
    public long saveBook(Book book) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            book.setId(allocateNewId(session));
            XmlUtils.addElement(XmlUtils.createElement("book",
                                                        getAttributesMap(new EntityManager<>(book))),
                                                        session.getResource());
        }
        return book.getId();
    }

    @Override
    public Book getBook(long id) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("books list"),
                                                             Map.of("id", id));
            return parseXmlElement(element);
        }
    }

    @Override
    public List<Book> getBooks(Book pattern) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            List<Element> elements = XmlUtils.findEqualsElements(session.getResource().getRootElement().element("books list"),
                                                                 getAttributesMap(new EntityManager<>(pattern)));
            return elements.stream().map(this::parseXmlElement).collect(Collectors.toList());
        }
    }

    @Override
    public void updateBook(Book book) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("books list"),
                                                             Map.of("id", book.getId()));
            XmlUtils.setAttributes(element, getAttributesMap(new EntityManager<>(book)));
        }
    }

    @Override
    public void deleteBook(Book book) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("books list"),
                                                             Map.of("id", book.getId()));
            session.getResource().getRootElement().element("books list").remove(element);
        }
    }

    @Override
    public boolean isBookExist(Book book) throws DaoException {
        if (0 > book.getId())
            return (null != getBook(book.getId()));
        else
            return (0 != getBooks(book).size());
    }
}
