package ru.nsu.ccfit.malinovskii;

import javafx.application.Application;
import ru.nsu.ccfit.malinovskii.factory.Emulator.Emulator;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Emulator emulator;
    public static void main(String[] args) {
        System.out.println("DONE");
        // Парсинг аргументов командной строки
        String configName;
        if (args.length < 1) {
            configName = "/FactoryConfig.txt";
            System.out.println("DONE");
        } else if (args.length == 1) {
            configName = args[0];
        } else {
            //logger.error("Too much arguments");
            return;
        }


        // Создание фабрики и подготовка её к работе
        emulator = new Emulator();
        try {
            emulator.loadConfiguration(configName);
            System.out.println("DONE\n");
        } catch (Exception e) {
            //logger.error("Exception: ", e);
        }


        try {
            Application.launch(Emulator.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("Exception: ", e);
        }
    }
}