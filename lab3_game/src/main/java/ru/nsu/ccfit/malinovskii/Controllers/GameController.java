package ru.nsu.ccfit.malinovskii.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.ccfit.malinovskii.Main;
import ru.nsu.ccfit.malinovskii.Model.Grid;

import java.io.IOException;
import java.net.URL;

import static ru.nsu.ccfit.malinovskii.Controllers.Game.*;

public class GameController {
    @FXML
    public GridPane gameGrid;
    @FXML
    public Label nicknameLabel;
    @FXML
    public Label scoreLabel;

    @FXML
    public void gridUpdate(Grid[][] grid){
        for (int y = 0; y < 20; ++y){
            for (int x = 0; x < 10; ++x){
                Rectangle cell = (Rectangle) gameGrid.lookup("#cell" + y + x);
                if (grid[y][x].getState() == 0){
                    cell.setFill(Color.WHITE); //Установить белый цвет
                }
                else {
                    cell.setFill(Color.BLUE); //Установить другой цвет
                }
            }
        }
    }

    @FXML
    public void scoreUpdate(){
        scoreLabel.setText(String.valueOf(score));
    }

    @FXML
    public void endScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/views/end-view.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Game");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



    @FXML
    public void initialize() {
        nicknameLabel.setText(nickname);
        for (int y = 0; y < 20; ++y){
            for (int x = 0; x < 10; ++x){
                Rectangle cell = new Rectangle(28, 28);
                cell.setId("cell" + y + x);
                cell.setStroke(Color.BLACK); // Черная граница
                gameGrid.add(cell, x, y);
            }
        }


    }
}
