package ru.nsu.ccfit.malinovskii;

import javafx.application.Application;
import ru.nsu.ccfit.malinovskii.Controllers.Game;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Game game;
    public static void main(String[] args) {
        game = new Game();

        try {
            Application.launch(Game.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("Exception: ", e);
        }
    }
}