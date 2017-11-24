package org.kvp_bld_sck.BookDatabase.server.dao.impl.xml;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kvp_bld_sck.BookDatabase.server.dao.SessionDao;
import org.kvp_bld_sck.BookDatabase.server.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.UserSession;
import org.kvp_bld_sck.BookDatabase.entity.User;

import java.util.Calendar;
import java.util.Map;
import java.util.regex.Pattern;

public class SessionDaoImpl implements SessionDao {

    private static final String DATABASE_PATH = "./resources/sessions.xml";

    private Map<String, Object> getAttributesMap(UserSession userSession) {
        Map<String, Object> map = Map.of();
        if (null != userSession.getId())
            map.put("id", userSession.getId());
        if (null != userSession.getUser())
            map.put("userId", userSession.getUser().getId());
        return map;
    }

    private UserSession parseXmlElement(Element element) {
        UserSession session = new UserSession();
        Attribute attribute;
        attribute = element.attribute("id");
        if (null != attribute)
            session.setId((String) attribute.getData());
        attribute = element.attribute("userId");
        if (null != attribute)
            session.setUser(new User((Integer) attribute.getData()));
        return session;
    }

    private String allocateNewId(XmlDocumentSession session) {
        return DigestUtils.md5Hex(String.valueOf(Calendar.getInstance().getTime().getTime()));
    }

    private boolean isDocumentValid(Document document) {
        return (null != document)
                && (null != document.getRootElement())
                && (null != document.getRootElement().attribute("max_id"))
                && (Pattern.compile("^\\d+$").matcher(document.getRootElement().attribute("max_id").getValue()).matches())
                && (null != document.getRootElement().element("sessions list"));
    }

    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("session_db");
        Attribute id = DocumentHelper.createAttribute(root, "id", String.valueOf(0));
        Element entities = DocumentHelper.createElement("sessions list");

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
    public UserSession createSession(User user) throws DaoException {
        UserSession userSession = new UserSession(user);
        try (XmlDocumentSession session = openSession()) {
            userSession.setId(allocateNewId(session));
            XmlUtils.addElement(XmlUtils.createElement("userSession",
                    getAttributesMap(userSession)),
                    session.getResource());
        }
        return userSession;
    }

    @Override
    public UserSession getSession(User user) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("userSessions list"),
                    Map.of("userId", user.getId()));
            return parseXmlElement(element);
        }
    }

    public UserSession getSession(String id) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("userSessions list"),
                    Map.of("id", id));
            return parseXmlElement(element);
        }
    }

    @Override
    public void deleteSession(User user) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("userSessions list"),
                    Map.of("userId", user.getId()));
            session.getResource().getRootElement().element("sessions list").remove(element);
        }
    }

    @Override
    public void deleteSession(UserSession userSession) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("userSessions list"),
                    Map.of("id", userSession.getId()));
            session.getResource().getRootElement().element("sessions list").remove(element);
        }
    }

    @Override
    public boolean isSessionExist(UserSession userSession) throws DaoException {
        if (null != userSession.getUser())
            return null != getSession(userSession.getUser());
        return false;
    }

    @Override
    public User getUser(UserSession userSession) throws DaoException {
        return getSession(userSession.getId()).getUser();
    }
}
