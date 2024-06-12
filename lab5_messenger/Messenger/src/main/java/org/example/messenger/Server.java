package org.example.messenger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerSomething> serverList = new LinkedList<>(); // список всех нитей

    // Создаем два набора для сообщений
    public static LinkedList<String> dataMessages = new LinkedList<>();
    public static Set<String> userMessages = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server has started!");
        try (ServerSocket server = new ServerSocket(PORT);){
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                ServerSomething serverSomething = null;
                serverSomething = new ServerSomething(socket);
                serverList.add(serverSomething); // добавить новое соединенние в список
            }
        }
    }
}