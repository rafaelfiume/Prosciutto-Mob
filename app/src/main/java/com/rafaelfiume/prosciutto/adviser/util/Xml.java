package com.rafaelfiume.prosciutto.adviser.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static javax.xml.xpath.XPathConstants.STRING;

public final class Xml {

    private Xml() {
        // not instantiable
    }

    public static Document xmlFrom(String xml) throws Exception {
        Document xmlDoc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));

        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(xmlDoc), new DOMResult());
        return xmlDoc;
    }

    public static String getValueFrom(Node item, String xpath) throws XPathExpressionException {
        return (String) xpath().evaluate(xpath + "/text()", item, STRING);
    }

    public static XPath xpath() {
        return XPathFactory.newInstance().newXPath();
    }

}
