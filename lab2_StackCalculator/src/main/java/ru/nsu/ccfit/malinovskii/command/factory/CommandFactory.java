package ru.nsu.ccfit.malinovskii.command.factory;
import ru.nsu.ccfit.malinovskii.calculatorcommands.Command;
import ru.nsu.ccfit.malinovskii.exceptions.CommandNotFoundException;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandFactory implements Factory{
    private final Map<String, Class<? extends Command>> commandMap;           //Class<? extends Command> означает, что принимаются любые ? (неизвестные типы), extends (ограниченные) классом Command

    public CommandFactory() {
        commandMap = new HashMap<>();
    }
    @Override
    public void loadConfiguration(String configFile) throws Exception {
        Properties properties = new Properties();                                                   //Хэш-таблица, где объект = объект
        InputStream inputStream = CommandFactory.class.getResourceAsStream(configFile);
        properties.load(inputStream);

        for (String commandName : properties.stringPropertyNames()) {
            String className = properties.getProperty(commandName);
            Class<? extends Command> commandClass = Class.forName(className).asSubclass(Command.class);     //Class.forName(className) - загружает класс с указанным именем className. .asSubclass(Command.class) - проверяет, что загруженный класс является подклассом класса Command. Если это так, возвращается экземпляр типа Class<? extends Command>, который представляет класс commandClass.
            commandMap.put(commandName, commandClass);
        }
    }
    @Override
    public Command createCommand(String commandName) throws Exception {
        Class<? extends Command> commandClass = commandMap.get(commandName);
        if (commandClass != null) {
            return commandClass.getDeclaredConstructor().newInstance();
        }
        else {
            throw new CommandNotFoundException();
        }
    }
}