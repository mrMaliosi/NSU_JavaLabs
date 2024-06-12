package ru.nsu.ccfit.malinovskii.stackcalculator;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;
import ru.nsu.ccfit.malinovskii.calculatorcommands.Command;
import ru.nsu.ccfit.malinovskii.command.factory.CommandFactory;
import ru.nsu.ccfit.malinovskii.parser.CommandParser;

import static ru.nsu.ccfit.malinovskii.stackcalculator.Main.logger;


public class StackCalculator {

    private final CommandFactory factory;
    private final ExecutionContext context;
    private String fileName;


    public StackCalculator(CommandFactory factory) {
        this.factory = factory;
        this.context = new ExecutionContext();
    }
    public void Setup(String fileName) throws Exception {
        logger.info("Starting to configure the calculator...");
        SystemMessages.greetings();
        factory.loadConfiguration("/commands.properties");
        this.fileName = fileName;
        logger.info("DONE.");
    }

    public int calculate() throws Exception {
        CommandParser parser = new CommandParser(fileName);
        while (!context.isEnd) {
            parser.NextCommand(context);
            logger.info("Executing command: " + context.getCommandName());
            Command command = factory.createCommand(context.getCommandName());     //Фабрика без кэширования
            command.execute(context);
            logger.info("The end of executing" + context.getCommandName() + ".");
        }
        logger.info("Calculator has ended his work.");
        return 0;
    }

}
