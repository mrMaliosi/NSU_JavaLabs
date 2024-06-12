package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;
import ru.nsu.ccfit.malinovskii.exceptions.PushException;

public class Push implements Command {
    @Override
    public void execute(ExecutionContext context) throws PushException {
        if (context.commandSize() == 2) {
            if (context.containsParametr(context.getFirstParametr())) {
                context.push(context.returnParametrValue(context.getFirstParametr()));
            }
            else
                try {
                    context.push(Double.parseDouble(context.getFirstParametr()));
                } catch (NumberFormatException e) {
                    throw new PushException("Unable to convert String to Double.");
            }
        } else {
            throw new PushException("Wrong number of arguments in command PUSH.");
        }
    }
}