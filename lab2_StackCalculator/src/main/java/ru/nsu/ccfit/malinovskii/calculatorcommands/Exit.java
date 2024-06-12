package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

public class Exit implements Command{
    @Override
    public void execute(ExecutionContext context)       //Добавь исключенния
    {
        context.isEnd = true;
    }
}
