package ru.nsu.ccfit.malinovskii.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.nsu.ccfit.malinovskii.Controllers.Game;
import ru.nsu.ccfit.malinovskii.Main;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static ru.nsu.ccfit.malinovskii.Controllers.Game.*;

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
            try {
                nickname = loginField.getText();
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/game-view.fxml");
                loader.setLocation(xmlUrl);
                Parent root = loader.load();
                gameController = loader.getController();
                Game game = Main.game;
                stage.setScene(new Scene(root));
                stage.setTitle("Game");
                gameThread = game.new doYouWannaPlay();
                gameThread.start();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        exitButton.setOnAction(e -> Platform.exit());
    }

}
