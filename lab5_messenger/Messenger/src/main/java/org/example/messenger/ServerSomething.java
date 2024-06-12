package org.example.messenger;

import MessageReader.MessageReader;
import MessageBuilder.MessageBuilder;
import MessageTypes.Message;
import javafx.application.Platform;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

import static org.example.messenger.Server.userMessages;

class ServerSomething extends Thread {
    private String login;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String message;
    private Message letter;
    private MessageBuilder messageBuilder;
    private MessageReader messageReader;
    private Boolean isConnected;

    public ServerSomething(Socket socket){
        System.out.println("New connection!");
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            message = "";
            messageBuilder = new MessageBuilder();
            messageReader = new MessageReader();
            isConnected = true;
            start();
        } catch (IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("running..");
        try {
            while (socket.isConnected() && isConnected) {
                this.receiveMessage();
                switch (letter.getType()){
                    case "login":
                        login = letter.getData();
                        this.sendLogins();
                        this.sendMessages();
                        this.setLoginMessage();
                        break;
                    case "message":
                        this.setDataMessage();
                        break;
                }
                if (!Objects.equals(letter.getType(), "empty")){
                    this.sendMessage();
                }
            }
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        } finally {
            Server.serverList.remove(this);
            try {
                if (in != null){
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null){
                    this.socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void send(String msg) {
        out.println(msg);
        out.flush();
    }

    private void setLoginMessage() throws TransformerException {
        message = messageBuilder.createLoginMessage(login);
    }

    private void setDataMessage() throws TransformerException {
        message = messageBuilder.createMessage(letter.getData(), login);
        userMessages.add(message);
    }

    private void receiveMessage() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        message = in.readLine();
        if (message != null){
            letter = messageReader.readMessage(message);
            System.out.println("message accepted");
        }
        else{
            letter.setType("empty");
            isConnected = false;
        }
    }

    private void sendMessage(){
        for (ServerSomething vr : Server.serverList) {
            System.out.println("Message has sent!");
            vr.send(message);
        }
    }

    private void sendLogins() throws TransformerException {
        for (ServerSomething vr : Server.serverList) {
            message = messageBuilder.createLoginMessage(vr.login);
            System.out.println("Message has sent!");
            this.send(message);
        }
    }

    private void sendMessages() {
        for (String letter : userMessages) {
            this.send(letter);
            System.out.println("sending " + letter);
        }
    }
}
