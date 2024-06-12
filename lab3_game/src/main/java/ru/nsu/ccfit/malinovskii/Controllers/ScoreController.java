package ru.nsu.ccfit.malinovskii.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.nsu.ccfit.malinovskii.Model.PlayerScore;

import java.io.*;
import java.net.URL;

import static ru.nsu.ccfit.malinovskii.Controllers.Game.stage;

public class ScoreController {
    public Button exitButton;
    @FXML
    private TableView<PlayerScore> scoreTable;
    @FXML
    private TableColumn<PlayerScore, String> playerColumn;
    @FXML
    private TableColumn<PlayerScore, Integer> scoreColumn;

    @FXML
    public void initialize() {
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("player"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<PlayerScore> data = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String player = parts[0];
                int score = Integer.parseInt(parts[1]);
                data.add(new PlayerScore(player, score));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoreTable.setItems(data);

        exitButton.setOnAction(e -> {
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
    }


}
