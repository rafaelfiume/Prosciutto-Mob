package com.rafaelfiume.prosciutto.adviser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;
import static javax.xml.xpath.XPathConstants.STRING;

public class ProductAdiviserParser {

    public List<Product> parse(String xml) throws Exception {
        final List<Product> suggestedProducts = new ArrayList<>();

        final NodeList productNode = (NodeList) xpath().evaluate("//product", xmlFrom(xml), NODESET);
        for (int i = 0; i < productNode.getLength(); i++) {
            Node item = productNode.item(i);
            suggestedProducts.add(new Product(nameFrom(item), priceFrom(item), reputationFrom(item), fatPercentageFrom(item)));
        }

        return suggestedProducts;
    }

    // Check if Exception is necessary

    private String nameFrom(Node item) throws Exception {
        final String node = (String) xpath().evaluate("name/text()", item, STRING);
        return node;
    }

    private String priceFrom(Node item) throws Exception {
        final Node node = (Node) xpath().evaluate("price/text()", item, NODE);
        return node.getNodeValue();
    }

    private String reputationFrom(Node item) throws Exception {
        final Node node = (Node) xpath().evaluate("reputation/text()", item, NODE);
        return node.getNodeValue();
    }

    private String fatPercentageFrom(Node item) throws Exception {
        final Node node = (Node) xpath().evaluate("fat-percentage/text()", item, NODE);
        return node.getNodeValue();
    }

    private Document xmlFrom(String xml) throws Exception {
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

    private XPath xpath() {
        return XPathFactory.newInstance().newXPath();
    }

    private static String prettyPrint(Node xml) throws Exception {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        final Writer out = new StringWriter();
        transformer.transform(new DOMSource(xml), new StreamResult(out));
        return out.toString();
    }
}
