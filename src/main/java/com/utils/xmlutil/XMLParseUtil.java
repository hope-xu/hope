package com.utils.xmlutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * @author Hope
 * Date： 2020/07/06  17:11
 * 描述：
 */
public class XMLParseUtil {
    private static final Logger log = LoggerFactory.getLogger(XMLParseUtil.class);
    private DocumentBuilder builder;

    private XPath xPath;

    /**
     * 获取实例
     * @throws ParserConfigurationException 创建实例报错
     */
    public XMLParseUtil() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        this.builder = documentFactory.newDocumentBuilder();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.xPath = xPathFactory.newXPath();
    }


    public Document parseDocument(String path) throws IOException, SAXException{
        return builder.parse(path);
    }


    public Document parseDocument(InputSource file) throws IOException, SAXException{
        return builder.parse(file);
    }











}
