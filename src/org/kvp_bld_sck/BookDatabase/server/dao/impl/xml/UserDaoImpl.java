package org.kvp_bld_sck.BookDatabase.server.dao.impl.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kvp_bld_sck.BookDatabase.server.dao.UserDao;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.User;
import org.kvp_bld_sck.BookDatabase.server.entitymanager.EntityManager;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {
    private static final String DATABASE_PATH = "./resources/users.xml";

    private Map<String, Object> getAttributesMap(EntityManager<User> userManager) {
        return userManager.getFieldsStream()
                .filter(field -> null != field.getValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private User parseXmlElement(Element element) {
        EntityManager<User> userManager = new EntityManager<>(new User());
        userManager.getFieldNamesStream().forEach(s -> userManager.suppressedSet(s, element.attribute(s).getData()));
        return userManager.getEntity();
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
                && (null != document.getRootElement().element("users list"));
    }

    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("user_db");
        Attribute id = DocumentHelper.createAttribute(root, "id", String.valueOf(0));
        Element entities = DocumentHelper.createElement("users list");

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
    public long saveUser(User user) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            user.setId(allocateNewId(session));
            XmlUtils.addElement(XmlUtils.createElement("user",
                    getAttributesMap(new EntityManager<>(user))),
                    session.getResource());
        }
        return user.getId();
    }

    @Override
    public User getUser(String username) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("users list"),
                    Map.of("username", username));
            return parseXmlElement(element);
        }
    }

    @Override
    public User getUser(long id) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("users list"),
                    Map.of("id", id));
            return parseXmlElement(element);
        }
    }
}
