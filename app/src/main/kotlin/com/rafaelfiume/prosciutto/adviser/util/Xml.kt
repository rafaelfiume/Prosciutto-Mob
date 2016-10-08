package com.rafaelfiume.prosciutto.adviser.util

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.InputSource

import java.io.StringReader

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMResult
import javax.xml.transform.dom.DOMSource
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory

import javax.xml.xpath.XPathConstants.STRING

object Xml {

    @Throws(Exception::class)
    fun xmlFrom(xml: String): Document {
        val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(InputSource(StringReader(xml)))

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
        transformer.transform(DOMSource(xmlDoc), DOMResult())
        return xmlDoc
    }

    @Throws(XPathExpressionException::class)
    fun getValueFrom(item: Node, xpath: String) = xpath().evaluate(xpath + "/text()", item, STRING) as String

    fun xpath(): XPath = XPathFactory.newInstance().newXPath()

}
