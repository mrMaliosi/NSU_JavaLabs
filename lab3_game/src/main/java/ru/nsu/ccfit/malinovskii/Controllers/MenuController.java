package ru.nsu.ccfit.malinovskii.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;

import static ru.nsu.ccfit.malinovskii.Controllers.Game.*;

public class MenuController {
    @FXML
    public Button exitButton;
    @FXML
    public Button scoreButton;
    @FXML
    public Button startButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/login-view.fxml");
                loader.setLocation(xmlUrl);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setTitle("Game");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        scoreButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/score-view.fxml");
                loader.setLocation(xmlUrl);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.setTitle("Game");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exitButton.setOnAction(e -> Platform.exit());
    }
}
