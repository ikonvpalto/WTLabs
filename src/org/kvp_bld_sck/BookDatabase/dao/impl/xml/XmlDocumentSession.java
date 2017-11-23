package org.kvp_bld_sck.BookDatabase.dao.impl.xml;

import org.dom4j.Document;
import org.kvp_bld_sck.BookDatabase.commons.Session;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;


public class XmlDocumentSession extends Session<Document> {

    protected String documentPath;

    public XmlDocumentSession(Document resource, String documentPath) {
        super(resource);
        this.documentPath = documentPath;
    }
    public static XmlDocumentSession openSession(String documentPath) throws DaoException {
        Document document = XmlUtils.loadDocument(documentPath);
        return new XmlDocumentSession(document, documentPath);
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public void close() throws DaoException {
        XmlUtils.saveDocument(resource, documentPath);
    }
}
