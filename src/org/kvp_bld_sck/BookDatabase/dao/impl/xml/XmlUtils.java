package org.kvp_bld_sck.BookDatabase.dao.impl.xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.kvp_bld_sck.BookDatabase.dao.exception.DaoException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlUtils {

    public static Document loadDocument(String path) throws DaoException {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(path);
        } catch (DocumentException e) {
            throw new DaoException("cannot load document from " + path, e);
        }
        return document;
    }

    public static void saveDocument(Document document, String path) throws DaoException {
        try (FileWriter out = new FileWriter("foo.xml")) {
            document.write(out);
        } catch (IOException e) {
            throw new DaoException("cannot save document to " + path, e);
        }
    }

    public static Document initNewDocument(String rootElement) {
        Document document = DocumentHelper.createDocument();
        document.addElement(rootElement);
        return document;
    }

    public static Document addElement(Element element, Document document) {
        document.getRootElement().add(element);
        return document;
    }

    public static Element createElement(String name, Map<String, Object> attributes) {
        Element element = DocumentHelper.createElement(name);
        setAttributes(element, attributes);
        return element;
    }

    public static void setAttributes(Element element, Map<String, Object> attributes) {
        for (Map.Entry<String, Object> attribute: attributes.entrySet())
            if (null == element.attribute(attribute.getKey())) {
                Attribute newAttribute = DocumentHelper.createAttribute(element, attribute.getKey(), "");
                newAttribute.setData(attribute.getValue());
            }
            else
                element.attribute(attribute.getKey()).setData(attribute.getValue());
    }

    public static boolean isElementEquals(Element element, Map<String, Object> attributeValues) {
        for (Map.Entry<String, Object> attributeValue : attributeValues.entrySet()) {
            Attribute attribute = element.attribute(attributeValue.getKey());
            if ((null == attribute) || (!attributeValue.getValue().equals(attribute.getData())))
                return false;
        }
        return true;
    }

    public static boolean isElementLikes(Element element, Map<String, String> attributeRegexes) {
        for (Map.Entry<String, String> attributeRegex : attributeRegexes.entrySet()) {
            Attribute attribute = element.attribute(attributeRegex.getKey());
            if ((null == attribute) || (!attribute.getValue().matches(attributeRegex.getValue())))
                return false;
        }
        return true;
    }

    public static List<Element> findEqualsElements(Element elementList, Map<String, Object> attributeValues) {
        Stream<Object> stream = elementList.elements().stream();
        Stream<Element> elementStream = stream
                                        .filter(o -> o instanceof Element)
                                        .map(Element.class::cast);
        List<Element> elements = elementStream.filter(e -> isElementEquals(e, attributeValues)).collect(Collectors.toList());
        return elements;
    }

    public static Element findFirstEqualElement(Element elementList, Map<String, Object> attributeValues) {
        Stream<Object> stream = elementList.elements().stream();
        Stream<Element> elementStream = stream
                                        .filter(o -> o instanceof Element)
                                        .map(Element.class::cast);
        return elementStream.filter(e -> isElementEquals(e, attributeValues)).findFirst().get();
    }

    public static List<Element> findLikeElements(Element elementList, Map<String, String> attributeRegexes) {
        Stream<Object> stream = elementList.elements().stream();
        Stream<Element> elementStream = stream
                .filter(o -> o instanceof Element)
                .map(Element.class::cast);
        return elementStream.filter(e -> isElementLikes(e, attributeRegexes)).collect(Collectors.toList());
    }

    public static Element findFirstLikeElement(Element elementList, Map<String, String> attributeRegexes) {
        Stream<Object> stream = elementList.elements().stream();
        Stream<Element> elementStream = stream
                .filter(o -> o instanceof Element)
                .map(Element.class::cast);
        return elementStream.filter(e -> isElementLikes(e, attributeRegexes)).findFirst().get();
    }

}
