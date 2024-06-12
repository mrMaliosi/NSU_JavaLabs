package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

public class Mult implements Command{
    @Override
    public void execute(ExecutionContext context)       //Добавь исключенния
    {
        double a = context.pop();
        double b = context.pop();
        context.push(b * a);
    }
}