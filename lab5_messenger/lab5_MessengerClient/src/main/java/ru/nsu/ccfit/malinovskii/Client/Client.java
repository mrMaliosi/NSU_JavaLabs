package ru.nsu.ccfit.malinovskii.Client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.malinovskii.MessageBuilder.MessageBuilder;
import ru.nsu.ccfit.malinovskii.MessageReader.MessageReader;
import ru.nsu.ccfit.malinovskii.MessageTypes.Message;
import ru.nsu.ccfit.malinovskii.MessageTypes.Text;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;

import static ru.nsu.ccfit.malinovskii.Main.client;


public class Client extends Application {
    public static Stage stage;
    static ClientController clientController;
    public static FXMLLoader loader = new FXMLLoader();


    static String login;
    static Socket clientSocket;     // сокет для общения
    static BufferedReader reader;   // ридер читающий с консоли
    static BufferedReader in;       // поток чтения из сокета
    static PrintWriter out;         // поток записи в сокет
    static int isConnected = -1;    // Троичная логика

    static Boolean controllerLock = true;
    Boolean isUnlocked = false;

    static Boolean isControllerReady = false;
    static final Text controllerReadyFlag = new Text("empty");

    static Boolean isMessageReady = false;
    static final Text text = new Text("empty");

    static Thread connectThread;
    Thread readThread;
    Thread writeThread;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/login-view.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("JustSimpleMessenger");
        stage.show();
    }

    class Connect extends Thread {
        @Override
        public void run(){
            try {
                try {
                    clientSocket = new Socket("localhost", 8080); // этой строкой мы запрашиваем у сервера доступ на соединение
                    reader = new BufferedReader(new InputStreamReader(System.in));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());

                    readThread = new ReadMsg();
                    writeThread = new WriteMsg();
                    readThread.start();
                    writeThread.start();
                    synchronized (Client.this) {
                        isConnected = 1;
                        controllerLock = false;
                        Client.this.notifyAll();
                    }

                    System.out.println("Client has started!");
                    // Ждем, пока оба потока не завершатся
                    readThread.join();
                    writeThread.join();
                } catch (InterruptedException e) {
                    if (in != null){
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    isConnected = -1;
                } finally { // в любом случае необходимо закрыть сокет и потоки
                    System.out.println("Client has closed...");
                    if (in != null){
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    isConnected = -1;
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    // нить чтения сообщений с сервера
    static class ReadMsg extends Thread {
        MessageReader messageReader;
        String str;
        Message letter;

        @Override
        public void run() {
            try {
                this.initialize();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

            this.isAllReady();

            while (true){
                try {
                    this.receiveMessage();
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
                    throw new RuntimeException(e);
                }

                switch (letter.getType()){
                    case "login":
                        this.getLoginMessage();
                        break;
                    case "message":
                        this.getDataMessage();
                        break;
                }
            }
        }

        private void initialize() throws ParserConfigurationException {
            messageReader = new MessageReader();
            str = "";
            letter = new Message();
        }

        private void isAllReady(){
            while (!isControllerReady){
                synchronized (controllerReadyFlag) {
                    try {
                        controllerReadyFlag.wait();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        private void receiveMessage() throws IOException, ParserConfigurationException, TransformerException, SAXException {
            str = Client.in.readLine(); // ждем сообщения с сервера
            letter = messageReader.readMessage(str);
        }

        private void getLoginMessage() {
            Platform.runLater(() -> {
                clientController.updateUserList(letter.getData());
            });
        }

        private void getDataMessage()  {
            Platform.runLater(() -> {
                clientController.updateMessageList(letter);
                System.out.println("DONE");
            });
            System.out.println(str); // печатаем сообщение с сервера на консоль
        }
    }


    // нить отправляющая сообщения приходящие с консоли на сервер
    static class WriteMsg extends Thread {
        @Override
        public void run() {
            try {
                MessageBuilder messageBuilder = new MessageBuilder();
                String message = messageBuilder.createLoginMessage(login);      //Отправили login
                System.out.println(message);
                Client.out.println(message);
                Client.out.flush();

                while (true) {
                    try {
                        while(!isMessageReady){
                            synchronized (client.text) {
                                Client.text.wait();
                            }
                        }
                        isMessageReady = false;
                        message =  messageBuilder.createMessage(client.text.getMessage(), login);
                        System.out.println(message);
                        Client.out.println(message);                  // отправляем на сервер
                        System.out.println("Sent.");
                        Client.out.flush(); // чистим
                    } catch (TransformerException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void stop() {
        if (readThread != null){
            readThread.interrupt();
        }
        if (writeThread != null){
            writeThread.interrupt();
        }
        if (connectThread != null){
            connectThread.interrupt();
        }

        Platform.exit();
        System.exit(0);
    }
}
