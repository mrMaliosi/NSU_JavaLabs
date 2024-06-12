package ru.nsu.ccfit.malinovskii.Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.nsu.ccfit.malinovskii.Main;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static ru.nsu.ccfit.malinovskii.Client.Client.*;

public class LoginController {

    @FXML
    public Button exitButton;
    @FXML
    public Button loginButton;
    @FXML
    public TextField loginField;
    @FXML
    public Label errorField;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> {
            if (!Objects.equals(loginField.getText(), ""))
            {
                login = loginField.getText();
                Client client = Main.client;
                isConnected = 0;
                connectThread = client.new Connect();
                connectThread.start();
                synchronized (client) {
                    while (controllerLock){
                        try {
                            client.wait();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                if ((client.clientSocket != null) && (client.clientSocket.isConnected()))
                {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/client-view.fxml");
                        loader.setLocation(xmlUrl);
                        Parent root = loader.load();
                        stage.setScene(new Scene(root));
                        stage.setTitle("JustSimpleMessenger");
                        clientController = loader.getController();
                        isControllerReady = true;
                        synchronized (controllerReadyFlag) {
                            controllerReadyFlag.notifyAll();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else {
                    errorField.setText("ERROR: UNABLE TO CONNECT TO SERVER!");
                }
            }
            else{
                errorField.setText("ERROR: EMPTY LOGIN!");
            }
        });
        exitButton.setOnAction(e -> Platform.exit());
    }

}
