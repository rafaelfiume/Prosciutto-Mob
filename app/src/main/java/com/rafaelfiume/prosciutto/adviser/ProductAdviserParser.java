package com.rafaelfiume.prosciutto.adviser;

import android.support.annotation.NonNull;

import com.rafaelfiume.prosciutto.adviser.domain.Product;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static javax.xml.xpath.XPathConstants.NODESET;
import static javax.xml.xpath.XPathConstants.STRING;

public class ProductAdviserParser {

    public List<Product> parse(String xml) {
        try {
            return doParse(xml);
        } catch (Exception e) {
            throw new RuntimeException("could not parse advice xml: " + xml, e);
        }
    }

    public List<Product> doParse(String xml) throws Exception {
        final List<Product> suggestedProducts = new ArrayList<>();

        final NodeList productsNode = (NodeList) xpath().evaluate("//product", xmlFrom(xml), NODESET);
        for (int i = 0; i < productsNode.getLength(); i++) {
            Node pNode = productsNode.item(i);
            suggestedProducts.add(newProductFrom(pNode));
        }

        return suggestedProducts;
    }

    @NonNull
    private Product newProductFrom(Node item) throws XPathExpressionException {
        return new Product(
                getValueFrom(item, "name")
                , getValueFrom(item, "price")
                , getValueFrom(item, "reputation")
                , getValueFrom(item, "fat-percentage")
        );
    }

    private String getValueFrom(Node item, String xpath) throws XPathExpressionException {
        return (String) xpath().evaluate(xpath + "/text()", item, STRING);
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

}
