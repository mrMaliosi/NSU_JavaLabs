package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

public class Define implements Command{
    @Override
    public void execute(ExecutionContext context)
    {
        context.parametrsPut();
    }
}
