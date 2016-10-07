package com.rafaelfiume.prosciutto.adviser

import com.rafaelfiume.prosciutto.adviser.domain.Product

import org.w3c.dom.Node
import org.w3c.dom.NodeList

import java.util.ArrayList

import javax.xml.xpath.XPathExpressionException

import com.rafaelfiume.prosciutto.adviser.util.Xml.getValueFrom
import com.rafaelfiume.prosciutto.adviser.util.Xml.xmlFrom
import com.rafaelfiume.prosciutto.adviser.util.Xml.xpath
import javax.xml.xpath.XPathConstants.NODESET

class ProductAdviserParser {

    fun parse(xml: String): List<Product> {
        try {
            return doParse(xml)
        } catch (e: Exception) {
            throw RuntimeException("could not parse advice xml: " + xml, e)
        }
    }

    @Throws(Exception::class)
    fun doParse(xml: String): List<Product> {
        val suggestedProducts = ArrayList<Product>()

        val productsNode = xpath().evaluate("//product", xmlFrom(xml), NODESET) as NodeList
        for (i in 0..productsNode.length - 1) {
            val pNode = productsNode.item(i)
            suggestedProducts.add(newProductFrom(pNode))
        }

        return suggestedProducts
    }

    @Throws(XPathExpressionException::class)
    private fun newProductFrom(item: Node): Product {
        return Product(
                getValueFrom(item, "name"), getValueFrom(item, "variety"), getValueFrom(item, "price"), getValueFrom(item, "reputation"), getValueFrom(item, "fat-percentage"), getValueFrom(item, "image"), getValueFrom(item, "description"))
    }

}
