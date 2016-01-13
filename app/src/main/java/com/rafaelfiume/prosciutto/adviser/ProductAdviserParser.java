package com.rafaelfiume.prosciutto.adviser;

import android.support.annotation.NonNull;

import com.rafaelfiume.prosciutto.adviser.domain.Product;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import static com.rafaelfiume.prosciutto.adviser.util.Xml.getValueFrom;
import static com.rafaelfiume.prosciutto.adviser.util.Xml.xmlFrom;
import static com.rafaelfiume.prosciutto.adviser.util.Xml.xpath;
import static javax.xml.xpath.XPathConstants.NODESET;

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
                , getValueFrom(item, "variety")
                , getValueFrom(item, "price")
                , getValueFrom(item, "reputation")
                , getValueFrom(item, "fat-percentage")
                , getValueFrom(item, "description"));
    }

}
