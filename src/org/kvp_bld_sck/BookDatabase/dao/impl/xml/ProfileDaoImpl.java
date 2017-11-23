package org.kvp_bld_sck.BookDatabase.dao.impl.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kvp_bld_sck.BookDatabase.dao.ProfileDao;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;
import org.kvp_bld_sck.BookDatabase.entity.Profile;
import org.kvp_bld_sck.BookDatabase.entitymanager.EntityManager;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProfileDaoImpl implements ProfileDao {

    private static final String DATABASE_PATH = "./resources/profiles.xml";

    private Map<String, Object> getAttributesMap(EntityManager<Profile> profileManager) {
        return profileManager.getFieldsStream()
                             .filter(field -> null != field.getValue())
                             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Profile parseXmlElement(Element element) {
        EntityManager<Profile> profileManager = new EntityManager<>(new Profile());
        profileManager.getFieldNamesStream().forEach(s -> profileManager.suppressedSet(s, element.attribute(s).getData()));
        return profileManager.getEntity();
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
                && (null != document.getRootElement().element("profiles list"));
    }

    private Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("profile_db");
        Attribute id = DocumentHelper.createAttribute(root, "id", String.valueOf(0));
        Element entities = DocumentHelper.createElement("profiles list");

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
    public Profile getProfile(long id) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("profiles list"),
                    Map.of("id", id));
            return parseXmlElement(element);
        }
    }

    @Override
    public Profile getProfile(String fullName) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("profiles list"),
                    Map.of("fullName", fullName));
            return parseXmlElement(element);
        }
    }

    @Override
    public long saveProfile(Profile profile) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            profile.setId(allocateNewId(session));
            XmlUtils.addElement(XmlUtils.createElement("profile",
                    getAttributesMap(new EntityManager<>(profile))),
                    session.getResource());
        }
        return profile.getId();
    }

    @Override
    public void updateProfile(Profile profile) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("profiles list"),
                    Map.of("id", profile.getId()));
            XmlUtils.setAttributes(element, getAttributesMap(new EntityManager<>(profile)));
        }
    }

    @Override
    public void deleteProfile(long id) throws DaoException {
        try (XmlDocumentSession session = openSession()) {
            Element element = XmlUtils.findFirstEqualElement(session.getResource().getRootElement().element("profiles list"),
                    Map.of("id", id));
            session.getResource().getRootElement().element("profiles list").remove(element);
        }
    }
}
