package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;
import ru.nsu.ccfit.malinovskii.exceptions.CommandException;

public interface Command {
    void execute(ExecutionContext context) throws CommandException;
}

