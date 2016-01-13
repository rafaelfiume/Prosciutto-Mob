package com.rafaelfiume.prosciutto.adviser;

import android.util.Log;

import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription;

import static com.rafaelfiume.prosciutto.adviser.util.Xml.getValueFrom;
import static com.rafaelfiume.prosciutto.adviser.util.Xml.xmlFrom;

public class ProductDescriptionParser {

    public ProductDescription parse(String xml) {
        try {
            return new ProductDescription(getValueFrom(xmlFrom(xml), "//extract"));
        } catch (Exception e) {
            Log.e(ProductDescriptionParser.class.getName(), "error when parsing xml " + xml, e);
            throw new RuntimeException("could not parse advice xml: " + xml, e);
        }
    }

}
