package ru.nsu.ccfit.malinovskii.Client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import ru.nsu.ccfit.malinovskii.MessageTypes.Message;

import java.util.Objects;

import static ru.nsu.ccfit.malinovskii.Client.Client.login;
import static ru.nsu.ccfit.malinovskii.Main.client;

public class ClientController {

    @FXML
    public Button printButton;
    @FXML
    public ListView<String> usersList;
    @FXML
    public TextField messageField;
    @FXML
    public GridPane messagesGrid;

    private int messageCount = 0; // счетчик сообщений

    @FXML
    public void updateMessageList(Message message) {
        System.out.println("deb " + message.getLogin() + " " + message.getData());
        Label loginLabel = new Label(message.getLogin());
        if (Objects.equals(message.getLogin(), login)){
            loginLabel.setStyle("-fx-text-fill: blue;");                    // делаем текст синим
        }
        else {
            loginLabel.setStyle("-fx-text-fill: red;");
        }

        Label dataLabel = new Label(message.getData());
        //FontIcon icon = new FontIcon("fas-home"); // создаем иконку
        VBox messageBox = new VBox(3, loginLabel, dataLabel);       // добавляем иконку в VBox
        messagesGrid.add(messageBox, 0, messageCount++);            // добавляем VBox в нулевой столбец GridPane
    }

    @FXML
    public void updateUserList(String userName) {
        ObservableList<String> users = usersList.getItems();
        if (!users.contains(userName)) { // проверяем, есть ли уже такой пользователь в списке
            users.add(userName);
        }
    }


    @FXML
    public void initialize() {
        printButton.setOnAction(e -> {
            String message = messageField.getText();
            System.out.println(message);
            client.text.setMessage(message);
            synchronized (client.text) {
                client.isMessageReady = true;
                client.text.notifyAll();
            }
            messageField.clear();
        });
    }
}
