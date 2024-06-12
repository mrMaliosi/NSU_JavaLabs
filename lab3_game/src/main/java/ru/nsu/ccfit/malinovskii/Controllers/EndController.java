package ru.nsu.ccfit.malinovskii.Controllers;

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

import static ru.nsu.ccfit.malinovskii.Controllers.Game.*;

public class EndController {

    @FXML
    public Button exitButton;
    @FXML
    public Button menuButton;
    @FXML
    public Label nicknameLabel;
    @FXML
    public Label scoreLabel;


    @FXML
    public void initialize() {
        nicknameLabel.setText(nickname);
        scoreLabel.setText(String.valueOf(score));
        menuButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/menu-view.fxml");
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
