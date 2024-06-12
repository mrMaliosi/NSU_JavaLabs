package ru.nsu.ccfit.malinovskii.MessageReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.malinovskii.MessageTypes.Message;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

public class MessageReader {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;

    public MessageReader() throws ParserConfigurationException {
        this.factory = DocumentBuilderFactory.newInstance();
        this.builder = this.factory.newDocumentBuilder();
    }


    public Message readMessage(String message) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        // Создаем Document из строки XML
        System.out.println(message);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(message)));

        // Получаем элементы типа и данных из сообщения
        NodeList typeNodes = document.getElementsByTagName("type");
        NodeList dataNodes = document.getElementsByTagName("data");


        // Получаем текст из первых элементов типа и данных
        String type = typeNodes.item(0).getTextContent();
        String data = dataNodes.item(0).getTextContent();
        String login = "";

        if (Objects.equals(type, "message")){
            NodeList loginNodes = document.getElementsByTagName("login");
            login = loginNodes.item(0).getTextContent();
        }

        // Возвращаем строку с данными
        return new Message(type, data, login);
    }
}
