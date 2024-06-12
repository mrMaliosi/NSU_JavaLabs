package ru.nsu.ccfit.malinovskii.stackcalculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.malinovskii.command.factory.CommandFactory;

import java.io.IOException;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        // Парсинг аргументов командной строки
        String fileName;
        if (args.length < 1) {
            fileName = null;
        } else if (args.length == 1) {
            fileName = args[0];
        } else {
            logger.error("Too much arguments");
            return;
        }

        // Создание калькулятора и подготовка его к работе
        CommandFactory factory = new CommandFactory();
        StackCalculator calculator = new StackCalculator(factory);
        try {
            calculator.Setup(fileName);
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        // Работа калькулятора
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                logger.error("Exception: ", e);
            }
        }
    }
}