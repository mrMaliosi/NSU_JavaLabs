package ru.nsu.ccfit.malinovskii.MessageBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class MessageBuilder {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;

    public MessageBuilder() throws ParserConfigurationException {
        this.factory = DocumentBuilderFactory.newInstance();
        this.builder = this.factory.newDocumentBuilder();
    }


    public String createMessage(String message, String login) throws TransformerException {
        Document document = builder.newDocument();
        // Create root element
        Element root = document.createElement("request");

        document.appendChild(root);

        // Create book elements and add text content
        Element book1 = document.createElement("type");
        book1.appendChild(document.createTextNode("message"));
        Element book2 = document.createElement("data");
        book2.appendChild(document.createTextNode(message));
        Element book3 = document.createElement("login");
        book3.appendChild(document.createTextNode(login));
        root.appendChild(book1);
        root.appendChild(book2);
        root.appendChild(book3);

        // Convert Document to XML String
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }


    public String createLoginMessage(String message) throws TransformerException {
        Document document = builder.newDocument();
        // Create root element
        Element root = document.createElement("request");

        document.appendChild(root);

        // Create book elements and add text content
        Element book1 = document.createElement("type");
        book1.appendChild(document.createTextNode("login"));
        Element book2 = document.createElement("data");
        book2.appendChild(document.createTextNode(message));
        root.appendChild(book1);
        root.appendChild(book2);

        // Convert Document to XML String
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }
}
