package ru.nsu.ccfit.malinovskii;

import javafx.application.Application;
import ru.nsu.ccfit.malinovskii.Client.Client;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Client client;
    public static void main(String[] args) {
        client = new Client();

        try {
            Application.launch(Client.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("Exception: ", e);
        }
    }
}