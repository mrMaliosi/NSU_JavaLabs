package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

public class Pop implements Command {
    @Override
    public void execute(ExecutionContext context)
    {
        context.pop();
    }
}