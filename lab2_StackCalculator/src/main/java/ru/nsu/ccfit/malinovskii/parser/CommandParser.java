package ru.nsu.ccfit.malinovskii.parser;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class CommandParser {
    private final Scanner input;

    public CommandParser(String filename) throws IOException {
        if (filename == null) {
            input = new Scanner(System.in);
        } else {
            input = new Scanner(new FileInputStream(filename));
        }
    }

    public void NextCommand(ExecutionContext context) throws IOException {
        String line = input.nextLine();
        if (!line.isEmpty()) {
            context.commandClear();
            String[] parts = line.split(" ");
            context.commandAdd(parts);
        }
        else{
            context.isEnd = true;
        }
    }

    public void NextCommandTest(ExecutionContext context, String line) throws IOException {
        if (!line.isEmpty()) {
            context.commandClear();
            String[] parts = line.split(" ");
            context.commandAdd(parts);
        }
    }
}
