package ru.nsu.ccfit.malinovskii.command.factory;

import ru.nsu.ccfit.malinovskii.calculatorcommands.Command;
import ru.nsu.ccfit.malinovskii.exceptions.CommandNotFoundException;

import java.io.InputStream;
import java.util.Properties;

public interface Factory {
    public void loadConfiguration(String configFile) throws Exception;

    public Command createCommand(String commandName) throws Exception;
}
